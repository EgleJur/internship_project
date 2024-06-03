# Use Amazon Corretto 17 as the base image
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/device-service-0.0.1-SNAPSHOT.jar /app/device-service.jar

# Copy the application properties
COPY src/main/resources/application*.properties /app/

# Expose the port that your Spring Boot application will run on
EXPOSE 8090

# Command to run the JAR file
#ENTRYPOINT ["java", "-jar", "device-service.jar"]

# Command to run the JAR file with a delay
ENTRYPOINT ["sh", "-c", "sleep 30 && java -jar device-service.jar"]
