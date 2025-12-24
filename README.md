# 이커머스 프로젝트 MUNOVA

## 📌 전체 프로젝트 개요
국내 유명 이커머스 플랫폼을 벤치마킹한 신발 전문 이커머스 플랫폼 프로젝트입니다.

<br/>

### ⚙️ 개발 프로세스 - 애자일(Agile) 방법론 개발
  
  <img width="630" height="350" alt="MUNOVA-Agile" src="https://github.com/user-attachments/assets/29716130-b6c6-4979-81de-4e6437fb4318" />
  
> 요구사항 기반 기능 MVP를 빠르게 구현한 뒤, 단위·통합·부하 테스트를 통해 기능과 성능 지표를 검증하고,<br>
> 측정 결과로 나타난 병목을 기준으로 서버 구조 및 로직을 반복적으로 개선하는 Agile 방식으로 고도화를 진행했습니다.

<br/>

### 📚 기술 스택
  <img width="630" height="350" alt="단락 텍스트" src="https://github.com/user-attachments/assets/f5358b14-10ca-434d-a0ff-8f96cef3f3d3" />

<br/><br/>

### 🧱 시스템 아키텍처
<img width="630" height="350" alt="(구름 위) SPACE drawio" src="https://github.com/user-attachments/assets/bed33fc2-aff0-48d5-9151-a3a850121894" />

<br/><br/>

### 📊 테스트 환경
<img width="630" height="350" alt="MUNOVA (1)" src="https://github.com/user-attachments/assets/d3dea3e8-bbde-4611-9581-27ed2f5adca4" />

---

<br/><br/>

## 🔗 담당 도메인 - 채팅
### 핵심 기능
✅ __1:1 문의 채팅 기능__
- 특정 상품에 대한 판매자 실시간 1:1 문의 가능
- 상품별 채팅방 자동 생성 및 관리
<details>
<summary> 1:1 문의 채팅 시연 </summary>
  
https://github.com/user-attachments/assets/fc980d36-2779-4c4e-bece-fad9613a1079
</details>


✅ __그룹 채팅 기능__
- 관심 태그를 기준으로 그룹 채팅방 생성 및 관리
- 다수 사용자 실시간 메시지 처리
- 태그 기반 채팅방 검색 및 조건 필터링

<details>
<summary> 그룹 채팅 생성 및 관리 </summary>
  
https://github.com/user-attachments/assets/be7f5f32-c950-4ba2-8c1e-5a72432e98bc
</details>
<details>
<summary> 그룹 채팅 실시간 메시지 처리 </summary>

https://github.com/user-attachments/assets/0afa1370-1b5d-48d5-92e6-140b6862c5cb
</details>

<br/><br/>

## ⚒️ 채팅 기능 단계적 고도화
### 1️⃣ TCP WebSocket 연결 처리 한계 검증
#### 목표
- 메시지 처리 비용을 제외한 상태에서 대규모 동시 접속 burst를 수용 가능한지 검증<br>
  (TCP 3-way handshake → HTTP Upgrade → STOMP CONNECTED)
- 가정: 10초 내 15,000명 동시 접속 시도(≈ 1,500 conn/s)

#### 문제 상황
- __TCP 3way Handshake 실패 발생__
  - burst 상황에서 WebSocket 연결 지연 급증 및 성공률 급락
  - 일부 연결은 STOMP `CONNECTED` 이전 단계에서 실패했고, OS 레벨 에러(`No file descriptors available`)가 관측됨
    → 애플리케이션 로직 진입 이전(TCP accept/handshake 구간)에서 병목이 발생했다고 판단

#### 접근 방식
- __File descriptor(ulimit) 상향__
  - 기존 `ulimit -n=512` 로 인해 `accept()` 단계에서 신규 소켓을 할당하지 못해 실패 발생
    → 목표 동접(15,000) 및 JVM/프로세스 기본 사용량을 고려해 20,000으로 설정
- __TCP backlog 확장 (SYN queue / accept queue)__
  - burst 상황에서 handshake 대기열이 부족해 drop이 발생 및 재전송으로 인한 지연 누적
    → accept queue(=`somaxconn` 상한)은 1,500 conn/s 유입에서 순간 지연(accept 처리 지연/스파이크)을 1~2초 완충할 수 있도록 4,096으로 설정
    → SYN queue(`tcp_max_syn_backlog`)는 RTT/재전송/혼잡의 변동성이 크므로 accept보다 보수적으로 8,192로 설정

#### 결과
- __개선 전__

  <img width="630" height="350" alt="image" src="https://github.com/user-attachments/assets/0aad0b36-e92d-44da-ab66-a0e81995111e" />
  
- __FD(ulimit) 튜닝 결과__
  
  <img width="630" height="350" alt="스크린샷 2025-12-24 153531" src="https://github.com/user-attachments/assets/03a7d2b2-fd85-481d-b579-119418c750bb" />

  - `No file descriptors available` 에러 제거
  - 모든 구간에서 성공률 개선 (5,000: 48% → 100%, 10,000: 30% → 96.7%, 15,000: 신규 측정 가능(92.5%))
  - ws_connecting / ws_connect_time 유의미한 감소를 보여주었으나 10k~15k 구간에서 여전히 수 초로 병목 발생

- __TCP backlog(somaxconn / tcp_max_syn_backlog) 튜닝 결과__

  - FD만 튜닝한 결과보다 성공률 증가
  - ws_connecting, ws_connect_time 단축
    5,000 VUs → ws_connecting: 2.39s → 1.15s, ws_connect_time: 3.63s → 1.67s
    15,000 VUs → ws_connecting: 10.76s → 7.44s, iteration_duration p95: 24.93s → 17.71s
