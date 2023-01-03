package com.tsyrkunou.jmpwep.application.service.ticketservice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderData;
import com.tsyrkunou.jmpwep.application.model.order.Oder;
import com.tsyrkunou.jmpwep.application.model.ticket.ReturnTicketData;
import com.tsyrkunou.jmpwep.application.model.ticket.ReturnTicketResponse;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.service.customerservice.CustomerService;
import com.tsyrkunou.jmpwep.application.service.eventservice.EventService;
import com.tsyrkunou.jmpwep.application.service.orderservice.OrderService;
import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.MyAppException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketBookingService {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final EventService eventService;
    private final TicketService ticketService;

    @Transactional
    public Oder createOrder(CreateOrderData createOrderData) {
        Customer customer;
        Event event;

        event = createOrderData.getEventId() != null ? eventService.findOne(createOrderData.getEventId())
                : eventService.findOne(createOrderData.getEventName());

        if (createOrderData.getNewCustomer() != null && createOrderData.getNewCustomer()) {
            customer = customerService.createCustomer(
                    new CustomerData(createOrderData.getCustomerName(), createOrderData.getCustomerBalance()));
        } else {
            customer =
                    createOrderData.getCustomerId() != null ? customerService.findOne(createOrderData.getCustomerId())
                            : customerService.findOne(createOrderData.getCustomerName());
        }

        validateOrderData(createOrderData, event, customer);

        if (createOrderData.getAmountOfPlace() != null) {
            List<Ticket> freeTicket = ticketService.getFreeTicket(event);
            return orderService.createOrder(event, customer, freeTicket, createOrderData.getAmountOfPlace());
        }
        return orderService.createOrder(event, customer, createOrderData.getNumberOfPlace());
    }

    private void validateOrderData(CreateOrderData createOrderData, Event event, Customer customer) {
        Integer amountOfPlace = createOrderData.getAmountOfPlace();
        List<Integer> numberOfPlace = createOrderData.getNumberOfPlace();
        if (amountOfPlace == null && numberOfPlace == null) {
            throw new MyAppException("Information about place not present");
        }
        long amountOfFreePlace = event.getTicket().stream().filter(Ticket::isFree).count();

        if (amountOfPlace != null && (event.getTicket().size() < amountOfPlace
                || amountOfFreePlace < amountOfPlace) || numberOfPlace != null && (
                event.getTicket().size() < numberOfPlace.size()
                        || amountOfFreePlace < numberOfPlace.size())
        ) {
            throw new MyAppException("No more free space");
        }
        if (amountOfPlace != null) {
            if (customer.getAmount().getBalance().compareTo(event.getTicket().stream().findFirst()
                    .get().getCoast().multiply(BigDecimal.valueOf(amountOfPlace))) < 0) {
                throw new MyAppException("insufficient funds");
            }
        } else if (
                !checkRequeredTicketForFreeAndEnoughBalance(numberOfPlace, customer.getAmount().getBalance())
        ) {
            throw new MyAppException("No more free space or insufficient funds");
        }
    }

    private boolean checkRequeredTicketForFreeAndEnoughBalance(List<Integer> numberOfPlace, BigDecimal balance) {
        List<Ticket> ticketByNumberOfPlace = ticketService.getTicketByNumberOfPlace(numberOfPlace);
        if (ticketByNumberOfPlace.size() != numberOfPlace.size()) {
            return false;
        }

        BigDecimal totalCoastOfTicket = BigDecimal.ZERO;
        for (Ticket ticket : ticketByNumberOfPlace) {
            totalCoastOfTicket = totalCoastOfTicket.add(ticket.getCoast());
        }
        return balance.compareTo(totalCoastOfTicket) >= 0;
    }

    private int generateRandomBalance(BigDecimal customerBalance) {
        if (customerBalance == null || customerBalance.equals(BigDecimal.ZERO)) {
            Random random = new Random();
            return random.nextInt((1000 - 100) + 1) + 100;
        }
        return customerBalance.intValue();
    }

    @Transactional
    public ReturnTicketResponse returnTicket(ReturnTicketData returnTicketData) {
        Long ticketId = returnTicketData.getTicketId();
        Customer customer = customerService.findByTicketId(ticketId);
        Ticket ticket = ticketService.findOne(ticketId);
        Oder order = orderService.findOderByCustomerId(customer.getId());
        orderService.refuseOrder(customer, ticket, order);
        return new ReturnTicketResponse(ticketId, ticket.isFree(), ticket.getEvent().getName(),
                customer.getAmount().getId());
    }
}
