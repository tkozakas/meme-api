FROM docker.io/maven:3-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/
RUN mvn package -DskipTests

RUN java -Djarmode=layertools -jar target/meme-api*.jar extract

FROM docker.io/eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN apk update \
  && apk add --no-cache ffmpeg

COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

RUN addgroup meme-api \
  && adduser --ingroup meme-api --disabled-password meme-api \
  && chown -R meme-api:meme-api /app

USER meme-api

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

# docker build -t tomas6446/meme-api:master .
