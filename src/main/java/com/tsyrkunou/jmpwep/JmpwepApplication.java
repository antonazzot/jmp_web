package com.tsyrkunou.jmpwep;

import org.hibernate.annotations.BatchSize;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableCaching
@SpringBootApplication
@EnableBatchProcessing
@BatchSize(size = 4)
@BatchDataSource
public class JmpwepApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmpwepApplication.class, args);
    }

}
