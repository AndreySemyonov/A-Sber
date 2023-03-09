package ru.astondevs.asber.userservice;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

public class ExternalSystemConfig {
    public static class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.0"));

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            Startables.deepStart(postgres).join();

            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }

    public static class RedisInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        private static final GenericContainer<?> redis = new GenericContainer<>
                (DockerImageName.parse("redis:5.0.3-alpine"))
                .withExposedPorts(6379);

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            redis.start();
            System.setProperty("spring.redis.host", redis.getHost());
            System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
        }
    }
}
