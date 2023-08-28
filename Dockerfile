FROM openjdk:17-alpine

WORKDIR /app

COPY target/teste-back-end-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
