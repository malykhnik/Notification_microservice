FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Notification_microservice-0.0.1-SNAPSHOT.jar /app/notification-microservice.jar

ENTRYPOINT ["java", "-jar", "notification-microservice.jar"]