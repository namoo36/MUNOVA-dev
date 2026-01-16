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

### 📊 테스트 환경 및 성능 목표
<img width="630" height="350" alt="MUNOVA (1)" src="https://github.com/user-attachments/assets/d3dea3e8-bbde-4611-9581-27ed2f5adca4" />

> 📖 자세한 설계 및 성능 테스트 내용은
> [테스트 환경 및 성능 목표](https://github.com/namoo36/MUNOVA-dev/wiki/%EB%B6%80%ED%95%98%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%84%B1%EB%8A%A5-%EB%AA%A9%ED%91%9C-%EB%B0%8F-%ED%85%8C%EC%8A%A4%ED%8A%B8-%ED%99%98%EA%B2%BD)를 참고하세요.

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
#### 1. 목표
대규모 실시간 채팅 서비스를 가정하여, 메시지 처리 비용을 제외한 순수 WebSocket 연결 수립 및 유지 능력을 검증했습니다. OS 커널 → 네트워크 큐 → 애플리케이션 설정까지 연결 수립 단계별 병목을 분리 분석하고, 로컬 환경에서 15,000 VUs 동시 접속을 안정적으로 수립·유지하는 것을 목표로 테스트를 진행했습니다.
> 본 테스트에서 테스트 환경 및 동시 접속자 수 산정 기준은 [⚙️ 테스트 환경 및 성능 목표](https://github.com/namoo36/MUNOVA-dev/wiki/%EB%B6%80%ED%95%98%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%84%B1%EB%8A%A5-%EB%AA%A9%ED%91%9C-%EB%B0%8F-%ED%85%8C%EC%8A%A4%ED%8A%B8-%ED%99%98%EA%B2%BD) 문서의 기준을 따릅니다.
<br>

#### 2. 문제 상황 및 접근
__1) OS 레벨 — File Descriptor 한계__
- __문제__: Burst 구간에서 `No file descriptors available` 에러 발생
- __원인__: 신규 소켓에 대한 FD 할당 실패로 `accept()` 단계에서 연결 탈락
- __조치__: `ulimit -n` 상향 (1,024 → 20,000)

__2) 네트워크 레벨 — TCP Backlog 병목__
- __문제__: 초기 burst 트래픽에서 Handshake 지연 및 재전송 발생
- __원인__: `SYN backlog`, `accept queue` 포화
- __조치__: `net.core.somaxconn` 확장, `tcp_max_syn_backlog` 확장, `Tomcat acceptCount` 조정

__3) 애플리케이션 레벨 — 동시 연결 상한__
- __문제__: 연결 성공 수(succeeded)가 8,192에서 정체
- __원인__: Tomcat 기본 `maxConnections=8192`
- __조치__: `maxConnections`를 20,000으로 확장

<br>

#### 3. 결과
__1)TCP / Handshake 단계 성능 비교__
- FD는 ‘연결 실패 제거’, TCP Backlog는 ‘지연 안정화’에 결정적 역할

| VUs        | 구분         | 연결 성공률 (`succeeded`) | `ws_connecting` 평균 | `ws_connect_time` 평균 |
| ---------- | ---------- | -------------------- | ------------------ | -------------------- |
| **5,000**  | 개선 전       | 48.36% (2,582)       | 11.42s             | 8.04s                |
|            | FD 튜닝      | **100% (5,000)**     | **2.39s (-79%)**   | 3.63s                |
|            | Backlog 튜닝 | **100% (5,000)**     | **1.15s (-51.9%)** | **1.68s**            |
|            |            |                      |                    |                      |
| **10,000** | 개선 전       | 30.18% (3,018)       | 13.26s             | 8.82s                |
|            | FD 튜닝      | **96.72% (9,672)**   | **4.68s (-64.7%)** | 6.20s                |
|            | Backlog 튜닝 | **99.18% (9,918)**   | **4.29s**          | **6.02s**            |
|            |            |                      |                    |                      |
| **15,000** | 개선 전       | 측정 불가                | -                  | -                    |
|            | FD 튜닝      | 92.54% (13,882)      | 10.76s             | 15.06s               |
|            | Backlog 튜닝 | **96.27% (14,441)**  | **7.44s (-30.9%)** | **10.38s**           |

__2) WebSocket 동시 연결 유지 한계 비교__
- 연결 유지 실패의 원인이 ‘WebSocket 구조’가 아닌 Tomcat 설정 상한(maxConnections)이었음을 확인

| VUs        | 구분   | 연결 성공률 (`succeeded`) | 세션 유지 시간 (`session_duration`) | 비고        |
| ---------- | ---- | -------------------- | ----------------------------- | --------- |
| **10,000** | 기본값  | 81.91% (8,191)       | 1m16s                         | 상한 도달     |
|            | 확장 후 | **100% (10,000)**    | **1m30s**                     | 목표 달성     |
|            |      |                      |                               |           |
| **15,000** | 기본값  | 54.60% (8,191)       | 56.29s                        | 상한 고정     |
|            | 확장 후 | **100% (15,000)**    | **1m30s**                     | 안정 유지     |
|            |      |                      |                               |           |
| **18,000** | 기본값  | 측정 불가                | -                             | -         |
|            | 확장 후 | 90.81% (16,347)      | 1m21s                         | 리소스 한계 진입 |

<br>

#### 4. 상세 분석(Wiki)
각 단계별 상세 설정 값, 그래프, 로그 분석, 튜닝 근거는 아래 문서에서 확인할 수 있습니다. <br>
➡️ [Phase 1. WebSocket(STOMP) 동시 접속 부하 테스트 분석](https://github.com/namoo36/MUNOVA-dev/wiki/Phase-1.-Websocket(STOMP)-%EB%8F%99%EC%8B%9C-%EC%A0%91%EC%86%8D-%EB%B6%80%ED%95%98-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EB%B6%84%EC%84%9D) <br>
➡️ [Full Report (Wiki)](https://github.com/namoo36/MUNOVA-dev/wiki) <br>

<br> 

### 2️⃣ STOMP(Servlet) vs RSocket(WebFlux·Netty) 메시지 처리 성능 비교

#### 1. 목표
WebSocket 기반 STOMP(Servlet) 구조와 `RSocket`(`WebFlux` + `Netty`) 구조의 메시지 처리 성능과 안정성을 검증했습니다. 최대 3,000 VUs 환경에서 1:1 채팅방 생성 → 메시지 전송 → DB 저장 → 수신 완료까지 `End-to-End` 흐름이 안정적으로 유지되는지 확인하고, 적합한 채팅 인프라 구조를 결정하는 것을 목표로 테스트를 진행했습니다.
> 본 테스트에서 테스트 환경 및 동시 접속자 수 산정 기준은 [⚙️ 테스트 환경 및 성능 목표](https://github.com/namoo36/MUNOVA-dev/wiki/%EB%B6%80%ED%95%98%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%84%B1%EB%8A%A5-%EB%AA%A9%ED%91%9C-%EB%B0%8F-%ED%85%8C%EC%8A%A4%ED%8A%B8-%ED%99%98%EA%B2%BD) 문서의 기준을 따릅니다.
<br>

#### 2. 문제 상황 및 접근
__1) 애플리케이션 레벨 — Servlet + STOMP 구조__
- __문제__: 1,500 VUs 이상에서 `HTTP API` 실패율 급증
- __원인__: `Thread-per-Request` 모델에서 DB I/O로 `Worker Thread` 장기 점유 → `Thread Pool` 고갈
- __특징__: `WebSocket` 세션은 유지되나, 채팅방 생성/메시지 저장 API 실패

__2) DB 레벨 — Blocking JDBC__
- __문제__: `INSERT/UPDATE` 중심 트랜잭션 폭증
- __원인__: `Connection Pool` 재사용 비효율 + `Lock` 경합
- __결과__: 처리 지연이 `HTTP/API` 병목으로 확산

__3) 구조적 한계 판단__
- `Servlet` 기반 동기 처리 + `DB Write` 결합 구조가 병목의 근본 원인이라 판단
- `STOMP` 프로토콜 프레임 구조로 인한 오버헤드 존재 

__4) 접근__
- __모델 전환(Servlet → WebFlux)__: HTTP 처리 모델을 Blocking Servlet → Non-Blocking Reactive Model로 전환
- __메시징 프로토콜 전환(STOMP → RSocket)__: `Request/Response` · `Fire&Forget` · `Streaming` 을 모두 지원하는 메시징 모델로 전환
- __세션 및 메시징 구조 재설계__: `RSocketRequester` 를 통해 사용자 별 세션 추적, 채팅방 단위 연결 관리, 개인 `Sink` 단위 메시지 제어가 가능하도록 재설계
- __DB Layer 전환(JDBC → R2DBC)__: 동기 `Blocking JDBC` → 비동기 `Non-Blocking R2DBC` 로 전환

<br>

#### 3. 결과
- `RSocket(Netty)` 구조 전환을 통해 동시 연결 수용 한계를 해소하고, 대규모 트래픽 환경에서도 안정적인 실시간 통신이 가능함을 수치로 검증했다.

|   VUs | 항목                       | STOMP (WebSocket + Tomcat) | RSocket (Netty)         |
| ----: | ------------------------ | -------------------------- | ----------------------- |
| 1,000 | 요청/세션 성공률                | 100% (58,637/58,637)       | 100% (1,000/1,000)      |
|       | 평균 API / Message Latency | 66.54 ms                   | 233.81 ms               |
|       | Connect Time             | 14.45 ms                   | 0.194 ms                |
|       | 처리 메시지 수                 | 58,637                     | 37,135 / 36,517         |
|    |                       |                         |                      |
| 1,500 | 요청/세션 성공률                | **58.64%** (실패 41.36%)     | **99.6%** (1,494/1,500) |
|       | 평균 API / Message Latency | **3.14 s**                 | 478.85 ms               |
|       | Connect Time             | 261.32 ms                  | 0.228 ms                |
|       | 처리 메시지 수                 | 78,817 / 78,867            | 129,434 / 114,210       |
|    |                       |                         |                      |
| 2,000 | 요청/세션 성공률                | **21.02%** (실패 78.98%)     | **99.6%** (1,992/2,000) |
|       | 평균 API / Message Latency | 106.98 ms                  | **7,697.79 ms**         |
|       | Connect Time             | 91.56 ms                   | 0.109 ms                |
|       | 처리 메시지 수                 | 116,135 / 116,135          | 129,583 / 64,706        |
|    |                       |                         |                      |
| 3,000 | 요청/세션 성공률                | 측정 불가                    | **99.6%** (2,988/3,000) |
|       | 평균 Message Latency       | 측정 불가                           | 225.63 ms               |
|       | Connect Time             | 측정 불가                         | 0.091 ms                |
|       | 처리 메시지 수                 | 측정 불가                           | 238,815 / 160,558       |
|       | E2E Startup              | -                        | 3,088 ms                |

<br>

#### 4. 상세 분석(Wiki)
각 단계별 상세 설정 값, 그래프, 로그 분석, 튜닝 근거는 아래 문서에서 확인할 수 있습니다. <br>
➡️ [Phase 2. RSocket(Netty) vs STOMP(Tomcat) 비교]([https://github.com/namoo36/MUNOVA-dev/wiki/Phase-1.-Websocket(STOMP)-%EB%8F%99%EC%8B%9C-%EC%A0%91%EC%86%8D-%EB%B6%80%ED%95%98-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EB%B6%84%EC%84%9D](https://github.com/namoo36/MUNOVA-dev/wiki/Phase-2.-RSocket(Netty)-vs-STOMP(Tomcat)-%EB%B9%84%EA%B5%90)) <br>
➡️ [Full Report (Wiki)](https://github.com/namoo36/MUNOVA-dev/wiki) <br>

<br>

### 3️⃣ 

