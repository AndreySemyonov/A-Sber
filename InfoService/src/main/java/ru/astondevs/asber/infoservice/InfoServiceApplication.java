package ru.astondevs.asber.infoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class that provides access to the application.
 */
@EnableScheduling
@SpringBootApplication
public class InfoServiceApplication {
    /**
     * Method that gets application's entry point.
     * @param args an array of command-line arguments for the application
     */
    public static void main(String[] args) {
        SpringApplication.run(InfoServiceApplication.class, args);
    }

}
