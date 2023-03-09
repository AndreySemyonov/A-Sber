FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY MoneyTransferServiceBuild/*.jar MoneyTransferService.jar
ENTRYPOINT ["java","-jar","/MoneyTransferService.jar"]