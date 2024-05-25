# Use an OpenJDK base image
FROM openjdk:23-ea-17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradlew /app/
COPY gradle /app/gradle
COPY build.gradle /app/
COPY settings.gradle /app/

# Copy the source code
COPY src /app/src

# Give execution permissions to the gradlew script
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew build 

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build/libs/payment-service.jar"]
