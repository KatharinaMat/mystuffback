# Use Java 21 runtime
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy everything
COPY . .

# Build the project (produces the jar in build/libs)
RUN ./gradlew clean build -x test

# --- Runtime image ---
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]