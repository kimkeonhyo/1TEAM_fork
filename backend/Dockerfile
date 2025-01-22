# 1. 베이스 이미지 (Java 17)
FROM amazoncorretto:17

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일 복사
COPY ./backend/backend-0.0.1-SNAPSHOT.jar app.jar

# 4. 실행 포트 노출
EXPOSE 8080

# 5. JAR 파일 실행
CMD ["java", "-jar", "app.jar"]