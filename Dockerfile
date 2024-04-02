FROM openjdk:17
LABEL maintainer = "payizzy"
WORKDIR /app
COPY demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]