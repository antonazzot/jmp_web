package com.tsyrkunou.jmpwep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableCaching
@SpringBootApplication
public class JmpwepApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmpwepApplication.class, args);
    }

}
