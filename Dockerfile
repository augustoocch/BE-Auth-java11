# Stage 1: Build the application
FROM maven:3.8.5-openjdk-11 AS builder
WORKDIR /app
COPY pom.xml .
COPY src/ src/
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
VOLUME /tmp
WORKDIR /app
ksjdfbvkjfsdbnvkjsbv.kjasbdfvk.jsdv

sdvsfv
