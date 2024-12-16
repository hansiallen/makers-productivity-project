# Java 21 base image
FROM eclipse-temurin:21 AS build

# Set the working directory for Gradle build
WORKDIR /app

# Copy the  source code to the container
COPY ./ .

RUN ./gradlew build -x test

# Stage 2: Use OpenJDK 21 as the runtime image
FROM openjdk:21-jdk-slim

# Set the working directory inside the runtime container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/ ./

# Expose the port the app will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "productivity-0.0.1-SNAPSHOT.jar"]
