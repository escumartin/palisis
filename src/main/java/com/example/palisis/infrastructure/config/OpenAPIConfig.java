package com.example.palisis.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Palisis User Management API")
                        .version("1.0.0")
                        .description("This is the RESTFull API documentation for the Palisis User Management System. " +
                                "The API allows users to manage their accounts and associated operation lines. " +
                                "All endpoints are secured and follow RESTfull best practices.")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Palisis Support")
                                .email("support@example.com")
                                .url("https://example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("https://api.example.com")
                                .description("Production Server"),
                        new Server()
                                .url("https://staging-api.example.com")
                                .description("Staging Server")
                ));
    }
}

