FROM gradle:latest AS build
WORKDIR /
COPY /src /src
COPY build.gradle /
RUN gradle clean build -x test

FROM openjdk:21-jdk-slim
WORKDIR /
COPY /src /src
COPY --from=build /build/libs/*.jar application.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "application.jar"]