# Stage 1: Build
FROM amazoncorretto:17 as builder

WORKDIR /app

COPY . .

# generated 폴더와 build 폴더 완전 삭제 & Q 파일 제거
RUN rm -rf /app/src/main/generated && \
    rm -rf /app/build && \
    find /app -name "Q*.java" -type f -delete

RUN ./gradlew clean
RUN ./gradlew build -x test

RUN ls -la /app/build/libs/  # JAR 파일 확인용 명령어


# Stage 2: Run
FROM amazoncorretto:17

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENV TZ Asia/Seoul

ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.config.location=file:/vault/secrets/application.yml"]
