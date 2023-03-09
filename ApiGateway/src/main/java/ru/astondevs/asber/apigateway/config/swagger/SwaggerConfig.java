package ru.astondevs.asber.apigateway.config.swagger;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Swagger Config that configures API documentation generation process for ApiGateway service.
 */
@Configuration
public class SwaggerConfig {
    /**
     * Method that gets resource from route definition.
     * @param routeLocator route locator resolves route configurations
     * @return SwaggerResourcesProvider reads the swagger-api JSON files from Swagger resource.
     */
    @Bean
    @Primary
    public SwaggerResourcesProvider swaggerResourcesProvider(
            RouteDefinitionLocator routeLocator
    ) {
        return () -> getResources(routeLocator);
    }

    private List<SwaggerResource> getResources(RouteDefinitionLocator routeLocator) {
        List<SwaggerResource> resources = new ArrayList<>();
        routeLocator.getRouteDefinitions().subscribe(routeDefinition ->
                getResourceFromDefinition(routeDefinition).ifPresent(resources::add)
        );
        return resources;
    }

    private Optional<SwaggerResource> getResourceFromDefinition(RouteDefinition routeDefinition) {
        String resourceName = routeDefinition.getId();
        Optional<String> locationOpt = routeDefinition.getPredicates().stream()
                .filter(predicate -> "Path".equals(predicate.getName()))
                .map(predicate -> predicate.getArgs().get("_genkey_0")
                        .replace("/**", "/v3/api-docs"))
                .findFirst();

        return locationOpt.map(location -> {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(resourceName);
            swaggerResource.setLocation(location);
            return swaggerResource;
        });
    }
}
