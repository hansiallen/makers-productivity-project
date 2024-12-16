# Java 21 base image
FROM eclipse-temurin:21

# Set the working directory for Gradle build
WORKDIR /app

# Copy the  source code to the container
COPY ./ .

RUN ./gradlew bootRun
