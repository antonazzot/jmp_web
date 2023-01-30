package com.tsyrkunou.jmpwep.application;

import java.time.Duration;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;
import com.tsyrkunou.jmpwep.application.repository.EventRepository;
import com.tsyrkunou.jmpwep.application.repository.OrderRepository;
import com.tsyrkunou.jmpwep.application.repository.TicketRepository;
import com.tsyrkunou.jmpwep.application.security.jwt.UserTokenRepository;
import com.tsyrkunou.jmpwep.application.security.service.AuthenticationService;
import com.tsyrkunou.jmpwep.application.service.customerservice.CustomerService;
import com.tsyrkunou.jmpwep.application.service.eventservice.EventService;
import com.tsyrkunou.jmpwep.application.service.orderservice.OrderService;
import com.tsyrkunou.jmpwep.application.service.ticketservice.TicketBookingService;
import com.tsyrkunou.jmpwep.application.service.ticketservice.TicketService;

import io.restassured.config.ObjectMapperConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.SneakyThrows;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@AutoConfigureMockMvc
public abstract class AbstractTest {

    public static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("create-schema-JMP.sql")
            .withUrlParam("currentSchema", "jmpdb")
            .withTmpFs(Collections.singletonMap("/var/lib/postgresql/data", "rw"))
            .withStartupTimeout(Duration.ofMinutes(1));

    static {
        POSTGRES.start();
        System.setProperty("spring.datasource.url", POSTGRES.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRES.getUsername());
        System.setProperty("spring.datasource.password", POSTGRES.getPassword());
    }

    @Autowired
    protected AuthenticationService authenticationService;

    @Autowired
    protected OrderService orderService;
    @Autowired
    protected EventService eventService;
    @Autowired
    protected TicketService ticketService;
    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected TicketBookingService ticketBookingService;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected EventRepository eventRepository;
    @Autowired
    protected TicketRepository ticketRepository;
    @Autowired
    protected UserTokenRepository userTokenRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    @SneakyThrows
    void setUpParent() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.config = RestAssuredMockMvc.config().objectMapperConfig(
                new ObjectMapperConfig().jackson2ObjectMapperFactory((type, s) -> {
                    var mapper = new ObjectMapper();
                    mapper.registerModule(new Jackson2HalModule());
                    mapper.registerModule(new JavaTimeModule());
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return mapper;
                }));
    }
}
