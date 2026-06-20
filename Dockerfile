# Build Stage
FROM eclipse-temurin:25-jdk-jammy AS builder
WORKDIR /app

# Copy the Gradle wrapper files
COPY gradlew .
COPY gradle gradle

# Copy the build configuration files
COPY build.gradle settings.gradle ./

# Copy the source code
COPY src src

# Make gradlew executable and build the jar (skipping tests for faster deployment)
RUN chmod +x gradlew
RUN ./gradlew build -x test

# Run Stage
FROM eclipse-temurin:25-jre-jammy
WORKDIR /app

# Copy the compiled jar from the build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port (matching server.port in application.properties)
EXPOSE 19090

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
