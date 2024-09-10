# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot app's JAR file into the container
COPY target/telecom-service-provisioning-0.0.1-SNAPSHOT.jar /app/telcoservice.jar

# Expose the port on which the Spring Boot app will run
EXPOSE 8082

# Define the command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/telcoservice.jar"]
