package com.example.project.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apartmentManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Apartment Management API")
                        .description("Backend API for apartment fee management system")
                        .version("v1.0")
                        .license(new License().name("MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Wiki")
                        .url("https://example.com"));
    }
}
