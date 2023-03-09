package ru.astondevs.asber.moneytransferservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.astondevs.asber.moneytransferservice.service.DepositServiceClient;
import ru.astondevs.asber.moneytransferservice.service.InfoServiceClient;

/**
 * Main class that provides access to the application.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(clients = {DepositServiceClient.class, InfoServiceClient.class})
public class MoneyTransferServiceApplication {
    /**
     * Method that gets application's entry point.
     * @param args an array of command-line arguments for the application
     */
    public static void main(String[] args) {
        SpringApplication.run(MoneyTransferServiceApplication.class, args);
    }

}
