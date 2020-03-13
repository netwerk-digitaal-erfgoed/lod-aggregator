# Build stage
FROM maven:3.6.0-jdk-11-slim AS build

WORKDIR /app/crawler

COPY src ./src
COPY libs ./libs
COPY pom.xml .
RUN mvn --quiet -e -f ./pom.xml clean assembly:assembly

# Package stage
FROM openjdk:11-jre-slim

WORKDIR /app/crawler

COPY --from=build /app/crawler/target/cc-lod-crawler-DockerApplication .
