## 📚 자세한 구현 설명은 블로그 시리즈를 참고하세요

이 프로젝트는 Spring Security를 완전 커스터마이징하여 MFA(2단계 인증)를 구현한 예제입니다.
실무에서 자주 쓰이는 OTP + JWT 구조를 멀티 모듈로 분리해 관리하는 구조를 익힐 수 있습니다.
코드 외에도 인증 흐름, 서버 구성, 테스트 결과 등을 **Postman 캡처와 함께 블로그 시리즈로 상세히 설명**해두었습니다.

[HOW_TO_RUN.md에 실행 방법 구체화 & 빠른 시작 안내 + API 테스트 방법이 나와있으니 참고해주세요.](https://github.com/grapeppodo/SpringSecurityAuthApp/blob/main/HOW_TO_RUN.md)

### ✨ 블로그 시리즈 구성

1. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 1: 시스템 아키텍처 및 3단계 인증 흐름](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-1-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EB%B0%8F-3%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%ED%9D%90%EB%A6%84)
2. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 2: 인증 서버를 구현해보자](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-2-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84%EB%A5%BC-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EC%9E%90)
3. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 3: 비즈니스 논리 서버를 구현해보자](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-3-%EB%B9%84%EC%A6%88%EB%8B%88%EC%8A%A4-%EB%85%BC%EB%A6%AC-%EC%84%9C%EB%B2%84%EB%A5%BC-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EC%9E%90)
4. [OTP 기반 2단계 인증 시스템 직접 구현해보기 - 4: 인증 서버와 비즈니스 서버 통합 테스트](https://dandev.tistory.com/entry/OTP-%EA%B8%B0%EB%B0%98-2%EB%8B%A8%EA%B3%84-%EC%9D%B8%EC%A6%9D-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0-4-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84%EC%99%80-%EB%B9%84%EC%A6%88%EB%8B%88%EC%8A%A4-%EB%85%BC%EB%A6%AC-%EC%84%9C%EB%B2%84%EA%B0%80-%EC%9E%98-%EB%8F%99%EC%9E%91%ED%95%98%EB%8A%94%EC%A7%80-%ED%99%95%EC%9D%B8%ED%95%B4%EB%B3%B4%EC%9E%90)
5. OTP 기반 2단계 인증 시스템 직접 구현해보기 - 5: 멀티 모듈 구조를 MSA 구조로 리팩토링해보자 *(작성중)*

---

> 이 시리즈는 **멀티 모듈 구조**와 **OTP 기반 MFA 구현**, **Spring Security 커스터마이징** 등의 과정을 단계별로 설명하고 있습니다.  
> 실습/개발 중 참고하거나 흐름을 이해하는 데 도움이 됩니다.


# MFA 인증 시스템 (Spring Security 기반 커스텀)

이 프로젝트는 Spring Security를 기반으로 직접 인증 로직을 커스텀한 **MFA(Multi-Factor Authentication) 시스템**입니다.

1차 인증은 `username/password`, 2차 인증은 `OTP` 방식으로 이루어지며, 최종 인증이 완료되면 클라이언트는 `JWT 토큰`을 발급받아 보호된 리소스에 접근할 수 있습니다.



## 📁 프로젝트 구조 (멀티 모듈)

이 프로젝트는 멀티 모듈 구조로 구성되어 있습니다.

```
SpringSecurityAuthApp/
├── auth       # OTP 생성 및 검증 등 인증 전용 서버
├── business   # 로그인, JWT 발급, 보호 리소스 처리 서버
```

## 🔧 기술 스택

- Java 17
- Spring Boot 3
- Spring Security 6 (커스터마이징)
- JPA 
- JWT (토큰 인증)
- MySQL
- Gradle
- Postman (테스트)

## 🧱 시스템 아키텍처 요약
1. 1차 인증: `username + password`
2. 2차 인증: `OTP` (일회용 비밀번호)
3. 인증 완료 후: `JWT` 토큰 발급 → 보호된 리소스 접근 가능


서버 구성:
- **인증 서버**: OTP 생성 및 검증
- **비즈니스 서버**: 사용자 요청 처리, JWT 보호 리소스 관리


### 인증 흐름 요약

1. `username/password`로 1차 인증 수행
2. 서버가 OTP를 생성하고 저장
3. 사용자가 OTP를 입력하면 2차 인증 수행
4. 인증 완료 시 JWT 발급 → 보호된 API 접근 가능

## ⚙️ 실행 방법

### application.properties 설정

`application.properties`는 Git에 포함되어 있지 않습니다. 
아래 정보를 참고하여 **설정**을 직접 추가해야 합니다.

`auth` 서버 기준:
```properties
spring.application.name=auth
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your_id
spring.datasource.password=your_pw

`business` 서버 기준:
```properties
server.port=9090
auth.server.base.url=http://localhost:8080
jwt.signing.key=your_jwt_secret
