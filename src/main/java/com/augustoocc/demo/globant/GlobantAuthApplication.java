package com.augustoocc.demo.globant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class GlobantAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobantAuthApplication.class, args);
    }

}
