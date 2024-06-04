package com.augustoocc.demo.globant.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.setName("Augusto Occhiuzzi");
        contact.setEmail("augusto.occhiuzzi@globant.com");
        contact.setUrl("https://www.linkedin.com/in/augusto-occhiuzzi-developer/");

        return new OpenAPI().info(new io.swagger.v3.oas.models.info.Info()
                .title("Globant Security Auth MS")
                .version("1.0")
                .contact(contact));
    }
}
