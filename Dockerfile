FROM maven:3.8.5-openjdk-17 AS build

# Copy the entire project
COPY . /app

# Set working directory
WORKDIR /app

# Build the Spring Boot application
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR file
COPY --from=build /app/target/*.jar /app/app.jar

# Install Python and virtual environment package
RUN apt-get update && apt-get install -y python3 python3-pip python3-venv

# Set the working directory for Python scripts
WORKDIR /app

COPY src/main/java/com/hris/HRIS /app/src/main/java/com/hris/HRIS

# Expose ports
EXPOSE 3269

# Define environment variable
ENV NAME World

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]