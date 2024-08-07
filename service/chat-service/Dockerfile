FROM openjdk:17-jdk-slim
VOLUME /tmp
RUN apt-get update && apt-get install -y curl
ADD ./build/libs/*.jar chat-server.jar
ENTRYPOINT ["java", "-jar", "/chat-server.jar"]