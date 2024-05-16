package com.augustoocc.demo.globant.config.swagger;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SpringFoxConfig {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.universityW3.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                //.paths(Predicate.not(PathSelectors.regex("/error.*")))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContext())
                .securitySchemes(securitySchemes());
    }

    private List<SecurityScheme> securitySchemes() {
        ApiKey jwt = new ApiKey("JWT", "Authorization", "header");
        return Collections.singletonList(jwt);
    }

    public List<SecurityContext> securityContext() {
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defautlAuth())
                .build();
        return Collections.singletonList(securityContext);
    }

    private List<SecurityReference> defautlAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverithing");
        AuthorizationScope[] authorizationsScopes = new AuthorizationScope[] {authorizationScope};
        SecurityReference securityReference = new SecurityReference("JWT",authorizationsScopes);
        return Collections.singletonList(securityReference);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact("Augusto", "http://globant.com", "augusto.occhiuzzi@globant.com"))
                .description("Auth API using Spring Boot to deploy in Minikube")
                .title("Globant SpringAuth")
                .build();
    }
}
