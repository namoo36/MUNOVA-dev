# 이커머스 프로젝트 MUNOVA

## 📌 전체 프로젝트 개요
국내 유명 이커머스 플랫폼을 벤치마킹한 신발 전문 이커머스 플랫폼 프로젝트입니다.

<br/>

### ⚙️ 개발 프로세스 - 애자일(Agile) 방법론 개발
  
  <img width="630" height="350" alt="MUNOVA-Agile" src="https://github.com/user-attachments/assets/29716130-b6c6-4979-81de-4e6437fb4318" />
  
> 요구사항 기반 기능 MVP를 빠르게 구현한 뒤, 단위·통합·부하 테스트를 통해 기능과 성능 지표를 검증하고,
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
### 1️⃣ TCP Handshake 병목 
#### 문제
- __TCP 3way Handshake 실패 발생__
  - 5,000 VUs 이상부터 WebSocket 연결 지연 급증 및 성공률 하락
  - `No file descriptors available` 에러 발생
- __
  
#### 해결
- __file descriptor(ulimit) 튜닝__
  - 서버의 기존 file descriptor(ulimit) 제한이 512로 설정 → `accept()` 단계에서 연결 실패


