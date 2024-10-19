# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim AS base

# Set the working directory in the container
WORKDIR /app

# Copy the Maven pom.xml first to leverage Docker caching
COPY pom.xml .

# Install Maven and dependencies
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy the source code into the container
COPY src ./src

# Build the application and skip tests
RUN mvn clean install -DskipTests

# List the contents of the target directory to ensure the JAR is created
RUN ls -l target/

# Copy the built JAR file to the image
COPY target/CortexTask-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the app runs on
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "app.jar"]
