# 🔐 MFA 인증 시스템 - Spring Security 기반 커스터마이징 프로젝트

이 프로젝트는 **Spring Security를 완전 커스터마이징하여 MFA(Multi-Factor Authentication)** 구조를 구현한 인증 시스템입니다.  
실무에서 많이 사용되는 **1차 인증(username/password) + 2차 인증(OTP)** → **JWT 발급 및 인가 처리** 구조를 기반으로,  
**멀티모듈 구조 → 멀티 애플리케이션 구조 → MSA 구조**로 확장 가능한 형태로 개발되었습니다.

> 📚 **블로그 시리즈에서 인증 흐름, 서버 구성, Postman 테스트 등 자세히 다루고 있습니다.**  
> [HOW_TO_RUN.md](./HOW_TO_RUN.md)에서 실행 방법과 빠른 시작 가이드를 참고하세요.


> 이 시리즈는 **멀티 모듈 구조**와 **OTP 기반 MFA 구현** 그리고 **Spring Security 커스터마이징** 등의 과정을 단계별로 설명하고 있습니다.  
> 실습/개발 중 참고하거나 흐름을 이해하는 데 도움이 됩니다.


## 📐 시스템 아키텍처

### 🔁 인증 흐름 요약

1. 사용자가 `username/password`로 로그인 시도 (1차 인증)
2. 서버가 OTP를 생성하여 사용자에게 전송 및 저장 (DB)
3. 사용자가 OTP 입력 → OTP 검증 (2차 인증)
4. 인증 완료 시 JWT 토큰 발급
5. JWT 토큰을 이용해 보호된 리소스 접근

### ⚙️ 서버 구성

```plaintext
┌────────────────────────────┐
│        클라이언트          │
└────────────┬───────────────┘
             │
     username/password
             ▼
┌────────────────────────────┐
│       인증 서버 (auth)     │
│  - 1차 인증                │
│  - OTP 생성/검증           │
└────────────┬───────────────┘
             │
     OTP 입력 / 검증
             ▼
┌────────────────────────────┐
│   JWT 발급 후 비즈니스 서버 │
│      (business)            │
│  - 보호 리소스 처리        │
│  - JWT 인가                │
└────────────────────────────┘
```

## 📁 프로젝트 구조 (멀티 모듈)

이 프로젝트는 멀티 모듈 구조로 구성되어 있습니다.

```
SpringSecurityAuthApp/
├── auth       # OTP 생성 및 검증 등 인증 전용 서버
├── business   # 로그인, JWT 발급, 보호 리소스 처리 서버
├── common     # 공통 모듈 (DTO, 예외, 설정 등)

```

## 🔧 기술 스택

- Java 17
- Spring Boot 3
- Spring Security 6 (커스터마이징)
- JWT (토큰 인증)
- JPA / MySQL
- Gradle
- Postman (API 테스트)
- Docker, Kubernetes (로컬 배포 테스트용)

서버 구성:
- **인증 서버**: OTP 생성 및 검증
- **비즈니스 서버**: 사용자 요청 처리, JWT 보호 리소스 관리

## ⚙️ 실행 방법

자세한 실행 방법은 HOW_TO_RUN.md를 참고하세요.

`auth` 서버 기준:
```properties
spring.application.name=auth
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your_id
spring.datasource.password=your_pw
```

`business` 서버 기준:
```properties
server.port=9090
auth.server.base.url=http://localhost:8080
jwt.signing.key=your_jwt_secret
```



## ✨ 블로그 시리즈 구성

1. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 1: 시스템 아키텍처 및 3단계 인증 흐름](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-1-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EB%B0%8F-3%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%ED%9D%90%EB%A6%84)
2. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 2: 인증 서버를 구현해보자](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-2-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84%EB%A5%BC-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EC%9E%90)
3. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 3: 비즈니스 논리 서버를 구현해보자](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-3-%EB%B9%84%EC%A6%88%EB%8B%88%EC%8A%A4-%EB%85%BC%EB%A6%AC-%EC%84%9C%EB%B2%84%EB%A5%BC-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EC%9E%90)
4. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 4: 인증 서버와 비즈니스 서버 통합 테스트](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-4-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84%EC%99%80-%EB%B9%84%EC%A6%88%EB%8B%88%EC%8A%A4-%EB%85%BC%EB%A6%AC-%EC%84%9C%EB%B2%84%EA%B0%80-%EC%9E%98-%EB%8F%99%EC%9E%91%ED%95%98%EB%8A%94%EC%A7%80-%ED%99%95%EC%9D%B8%ED%95%B4%EB%B3%B4%EC%9E%90)
5. OTP 기반 2단계 인증 시스템 직접 구현해보기 - 5: 멀티 모듈 구조를 MSA 구조로 리팩토링해보자

    5-1. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 5-1 현재 프로젝트의 구조와 MSA로 전환하기 위해 어떤 과정이 필요한지 알아보자.](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-5-1-%ED%98%84%EC%9E%AC-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EC%9D%98-%EA%B5%AC%EC%A1%B0%EC%99%80-MSA%EB%A1%9C-%EC%A0%84%ED%99%98%ED%95%98%EA%B8%B0-%EC%9C%84%ED%95%B4-%EC%96%B4%EB%96%A4-%EA%B3%BC%EC%A0%95%EC%9D%B4-%ED%95%84%EC%9A%94%ED%95%9C%EC%A7%80-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90)


    5-2. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 5-2 Docker를 구현해보자.](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-5-2-Docker%EB%A5%BC-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EC%9E%90)
