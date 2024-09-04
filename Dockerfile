FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/coursecanvasspring-0.0.1-SNAPSHOT.jar /app/coursecanvasspring.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/coursecanvasspring.jar"]
