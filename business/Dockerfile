# 1단계: 빌드 환경
FROM gradle:8.4.0-jdk17 AS builder
WORKDIR /app

# 소스 전체 복사 (build.gradle, settings.gradle, src 등)
COPY --chown=gradle:gradle . .

# gradle build 실행 (wrapper 있다면 ./gradlew)
RUN ./gradlew bootJar --no-daemon

# 2단계: 실행 환경
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드 결과물 복사
COPY wait-for-it.sh wait-for-it.sh
COPY build/libs/business-*.jar app.jar

RUN chmod +x wait-for-it.sh
RUN apt-get update && apt-get install -y bash   # for Debian/Ubuntu


ENTRYPOINT exec ./wait-for-it.sh auth:8080 -- java -jar app.jar
