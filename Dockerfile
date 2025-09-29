FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/task-manager-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV DB_USERNAME=root
ENV DB_PASSWORD=password
ENV JWT_SECRET=yourStrongSecretKey

ENTRYPOINT ["java", "-jar", "app.jar"]