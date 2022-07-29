package com.tsyrkunou.jmpwep.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderData;
import com.tsyrkunou.jmpwep.application.model.order.Oder;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.MyAppException;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketBookingService {
    private final OrderService orderService;
    private final EventService eventService;
    private final TicketService ticketService;
    private final CustomerService customerService;

    @Transactional
    public Oder createOrder(CreateOrderData createOrderData) {
        Customer customer;
        Event event;

        event = createOrderData.getEventId() != null ? eventService.findOne(createOrderData.getEventId())
                : eventService.findOne(createOrderData.getEventName());

        if (createOrderData.getNewCustomer() != null && createOrderData.getNewCustomer()) {
            customer = customerService.createCustomer(CustomerData.builder()
                    .name(createOrderData.getCustomerName())
                    .balance(BigDecimal.valueOf(generateRandomBalance(createOrderData.getCustomerBalance())))
                    .build());
        } else {
            customer =
                    createOrderData.getCustomerId() != null ? customerService.findOne(createOrderData.getCustomerId())
                            : customerService.findOne(createOrderData.getCustomerName());
        }

        validateOrderData(createOrderData, event, customer);

        List<Ticket> freeTicket = ticketService.getFreeTicket(event);

        return orderService.createOrder(event, customer, freeTicket, createOrderData.getAmountOfPlace());
    }

    private void validateOrderData(CreateOrderData createOrderData, Event event, Customer customer) {
        Integer amountOfPlace = createOrderData.getAmountOfPlace();
        if (event.getTicket().size() < amountOfPlace
                || event.getTicket().stream().filter(Ticket::isFree).count() < amountOfPlace) {
            throw new MyAppException("No more free space");
        }

        if (customer.getBalance().intValue() < amountOfPlace * event.getTicket().stream().findFirst()
                .get().getCoast().intValue()) {
            throw new MyAppException("insufficient funds");
        }

    }

    private int generateRandomBalance(BigDecimal customerBalance) {
        if (customerBalance == null || customerBalance.equals(BigDecimal.ZERO)) {
            Random random = new Random();
            return random.nextInt((1000 - 100) + 1) + 100;
        }
        return customerBalance.intValue();
    }
}
