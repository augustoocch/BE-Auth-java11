FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
VOLUME /tmp
WORKDIR /app
COPY pom.xml .
COPY src/ /app/src/
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=k8s", "-jar", "app.jar"]