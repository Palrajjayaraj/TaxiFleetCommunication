# Build Stage
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn -f -U clean package -DskipTests

# Run Stage
FROM openjdk:21-jdk-slim
WORKDIR /app/Sources

# Copy the JAR file and libs from the build stage
COPY management-webapp/target/*.jar app.jar
COPY management-webapp/target/libs libs

# Run with full classpath (thin JAR + libs)
ENTRYPOINT ["java", "-cp", "app.jar:libs/*", "com.pal.taxi.web.TaxiWebApplication"]
