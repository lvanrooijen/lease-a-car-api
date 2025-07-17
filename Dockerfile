FROM openjdk:17-jdk-alpine
LABEL authors="laila"

ARG JAR_FILE=target/*.jar

COPY target/lease-a-car-0.0.1-SNAPSHOT.jar lease-a-car.jar

ENTRYPOINT ["java", "-jar","/lease-a-car.jar"]