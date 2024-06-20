# 베이스 이미지로 OpenJDK 17 사용
FROM openjdk:17-jdk-alpine
ENV SPRING_PROFILES_ACTIVE=prod

# 작업 디렉토리 생성
WORKDIR /app

# 호스트 머신의 빌드된 JAR 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 포트 설정 (Spring Boot 애플리케이션이 사용하는 포트에 맞게 변경)
EXPOSE 8080

# 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]
