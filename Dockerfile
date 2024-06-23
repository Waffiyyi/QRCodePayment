FROM openjdk:17
LABEL maintainer = "payizzy"
WORKDIR /app
COPY qrcode-k8s.jar /app/qrcode-k8s.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "qrcode-k8s.jar"]