FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ApiGatewayBuild/*.jar ApiGateway.jar
ENTRYPOINT ["java","-jar","/ApiGateway.jar"]
