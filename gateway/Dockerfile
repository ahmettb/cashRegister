# Base image
FROM alpine/java:22.0.1-jdk
EXPOSE 8500

ARG JAR_FILE=target/gateway-0.0.1-SNAPSHOT.jar
# Set working directory
WORKDIR /opt/app

# Copy the packaged JAR file into the container
COPY ${JAR_FILE} gateway.jar
# Specify the command to run your application
CMD ["java", "-jar", "gateway.jar"]

