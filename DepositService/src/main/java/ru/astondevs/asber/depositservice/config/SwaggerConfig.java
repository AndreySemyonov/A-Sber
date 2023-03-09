package ru.astondevs.asber.depositservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * Swagger Config that configured API documentation generation process for deposit service.
 */
@Configuration
public class SwaggerConfig {
    /**
     * Method that defines path for access to API documentation, list of controllers for generate documentation.
     * @return {@link Docket}
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/deposit")
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.astondevs.asber.depositservice.controller"))
                .build()
                .securitySchemes(List.of(new ApiKey("Authorization", "Bearer", "header")));

    }
}
