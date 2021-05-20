FROM maven:3.6.2-jdk-8-slim AS builder
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/

RUN ["mvn", "clean", "install", "-DskipTests"]

FROM openjdk:8-jre
WORKDIR /app
COPY --from=builder /build/target/person-service-0.0.1.jar /app/
ENTRYPOINT ["java", "-jar", "person-service-0.0.1.jar"]