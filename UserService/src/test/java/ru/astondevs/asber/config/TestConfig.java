package ru.astondevs.asber.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import ru.astondevs.asber.userservice.util.TestServiceInstanceListSupplier;

@TestConfiguration
public class TestConfig {
    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new TestServiceInstanceListSupplier("deposit-service", 8082);
    }
}