package com.tsyrkunou.jmpwep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableCaching
@EnableFeignClients
@EnableOAuth2Sso
@EnableOAuth2Client
@SpringBootApplication
public class JmpwepApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmpwepApplication.class, args);
    }

}
