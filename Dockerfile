# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven pom.xml and the source code into the container
COPY pom.xml .
COPY src ./src

# Update package lists, install Maven, and build the application
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests && \
    apt-get clean && rm -rf /var/lib/apt/lists/*


COPY target/CortexTask-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the app runs on
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "app.jar"]
