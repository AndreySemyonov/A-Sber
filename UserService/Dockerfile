FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY UserServiceBuild/*.jar UserService.jar
ENTRYPOINT ["java","-jar","/UserService.jar"]
