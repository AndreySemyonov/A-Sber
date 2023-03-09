package ru.astondevs.asber.depositservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class that provides access to the application.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class DepositServiceApplication {
    /**
     * Method that gets application's entry point.
     * @param args an array of command-line arguments for the application
     */
    public static void main(String[] args) {
        SpringApplication.run(DepositServiceApplication.class, args);
    }

}
