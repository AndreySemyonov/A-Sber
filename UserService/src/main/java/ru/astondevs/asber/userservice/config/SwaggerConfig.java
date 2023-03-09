package ru.astondevs.asber.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * Swagger Config that configures API documentation generation process for user service.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Method for openAPI configuration for UserService
     *
     * @return {@link Docket}
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/user")
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.astondevs.asber.userservice.controller"))
                .build()
                .securitySchemes(List.of(new ApiKey("Authorization", "Bearer", "header")));
    }
}
