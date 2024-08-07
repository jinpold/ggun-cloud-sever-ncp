FROM openjdk:17-jdk-slim
VOLUME /tmp
RUN apt-get update && apt-get install -y curl
ADD ./build/libs/*.jar admin-server.jar
ENTRYPOINT ["java", "-jar", "/admin-server.jar"]