# =========================
# STAGE 1: BUILD
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Copy source
COPY src ./src

# Build jar
RUN mvn -B -q clean package -DskipTests

# =========================
# STAGE 2: ANALYZE + JLINK
# =========================
FROM eclipse-temurin:21-jdk-jammy AS jlink

WORKDIR /app

# Copy jar từ stage build
COPY --from=build /app/target/*.jar app.jar

# Extract jar (Spring Boot)
RUN jar xf app.jar

# Generate module dependencies
RUN jdeps \
    --ignore-missing-deps \
    -q \
    --recursive \
    --multi-release 21 \
    --print-module-deps \
    --class-path 'BOOT-INF/lib/*' \
    app.jar > deps.info

# Debug (optional)
RUN cat deps.info

# Build custom JRE
RUN jlink \
    --module-path $JAVA_HOME/jmods \
    --add-modules $(cat deps.info),java.naming,java.management,java.sql,java.xml \
    --compress=2 \
    --no-header-files \
    --no-man-pages \
    --strip-debug \
    --output /custom-jre

# =========================
# STAGE 3: RUNTIME
# =========================
FROM debian:bookworm-slim
WORKDIR /app
# Setup Java
ENV JAVA_HOME=/opt/java
# Copy custom JRE
COPY --from=jlink /custom-jre $JAVA_HOME
ENV PATH="$JAVA_HOME/bin:$PATH"

COPY --from=build /app/target/*.jar app.jar

# Security (optional nhưng nên có)
RUN useradd -m appuser
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
