package ru.astondevs.asber.creditservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Main class that provides access to the application.
 */
@SpringBootApplication
@EnableEurekaClient
public class CreditServiceApplication {

    /**
     * Method that gets application's entry point.
     *
     * @param args an array of command-line arguments for the application
     */
    public static void main(String[] args) {
        SpringApplication.run(CreditServiceApplication.class, args);
    }
}
