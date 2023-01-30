//package com.tsyrkunou.jmpwep.application.config;
//
//import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextListener;
//
//import com.tsyrkunou.jmpwep.application.model.customer.Customer;
//
//@Configuration
//public class RequestListener {
//    @Bean
//    public RequestContextListener requestContextListener() {
//        return new RequestContextListener();
//    }
//
//    @Bean
//    public PrincipalExtractor principalExtractor() {
//        return map -> {
//            return new Customer();
//        };
//    }
//}
