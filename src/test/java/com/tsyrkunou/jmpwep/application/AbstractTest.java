package com.tsyrkunou.jmpwep.application;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;
import com.tsyrkunou.jmpwep.application.repository.EventRepository;
import com.tsyrkunou.jmpwep.application.repository.OrderRepository;
import com.tsyrkunou.jmpwep.application.repository.TicketRepository;
import com.tsyrkunou.jmpwep.application.service.CustomerService;
import com.tsyrkunou.jmpwep.application.service.EventService;
import com.tsyrkunou.jmpwep.application.service.OrderService;
import com.tsyrkunou.jmpwep.application.service.TicketBookingService;
import com.tsyrkunou.jmpwep.application.service.TicketService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public abstract class AbstractTest {
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
    protected CustomerRepository customerRepository;


}
