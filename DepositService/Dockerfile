FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY DepositServiceBuild/*.jar DepositService.jar
ENTRYPOINT ["java","-jar","/DepositService.jar"]