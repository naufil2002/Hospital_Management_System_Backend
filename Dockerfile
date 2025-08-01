# Use a base image with Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set work directory in container
WORKDIR /app

# Copy Maven wrapper and pom
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy rest of the app
COPY src ./src

# Package app
RUN ./mvnw clean package -DskipTests

# Set jar file path
ARG JAR_FILE=target/hospital_management-0.0.1-SNAPSHOT.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "target/hospital_management-0.0.1-SNAPSHOT.jar"]
