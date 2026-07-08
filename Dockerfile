# Stage 1: Build the application using a Gradle image with JDK 26 support
FROM gradle:jdk26 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

# Stage 2: Create the runtime image using JDK 26
FROM eclipse-temurin:26-jdk
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]