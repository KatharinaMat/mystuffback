# =========================
# 1. BUILD STAGE
# =========================
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy all project files into the container
COPY . .

# Normalize line endings just in case (CRLF -> LF)
RUN sed -i 's/\r$//' gradlew

# Build the Spring Boot JAR using the wrapper via 'sh'
RUN sh ./gradlew clean build -x test


# =========================
# 2. RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the jar that was built in the previous stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose 8080 inside the container (Render maps its own port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]