# Java 21 base image
FROM eclipse-temurin:21

# Set the working directory for Gradle build
WORKDIR /app

# Copy the  source code to the container
COPY ./ .

RUN ./gradlew build -x test

# Copy the JAR file from the build stage
COPY /app/build/libs/productivity-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port the app will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
