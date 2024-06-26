# Base image
FROM alpine/java:22.0.1-jdk
EXPOSE 8761


ARG JAR_FILE=target/discovery-0.0.1-SNAPSHOT.jar
# Set working directory
WORKDIR /opt/app

# Copy the packaged JAR file into the container
COPY ${JAR_FILE} discovery.jar
# Specify the command to run your application
CMD ["java", "-jar", "discovery.jar"]

