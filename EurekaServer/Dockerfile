FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY EurekaServerBuild/*.jar EurekaServer.jar
ENTRYPOINT ["java","-jar","/EurekaServer.jar"]