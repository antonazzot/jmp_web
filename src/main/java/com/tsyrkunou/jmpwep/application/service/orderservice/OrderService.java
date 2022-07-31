package com.tsyrkunou.jmpwep.application.service.orderservice;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.amounts.customerbalance.Amount;
import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.amounts.eventbalance.EventAmount;
import com.tsyrkunou.jmpwep.application.model.order.Oder;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.repository.OrderRepository;
import com.tsyrkunou.jmpwep.application.service.amountservice.BalanceProcessor;
import com.tsyrkunou.jmpwep.application.service.customerservice.CustomerService;
import com.tsyrkunou.jmpwep.application.service.ticketservice.TicketService;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final TicketService ticketService;
    private final BalanceProcessor<EventAmount, Amount> balanceProcessor;

    @Transactional
    public Oder createOrder(Event event, Customer customer, List<Ticket> freeTicket, Integer amountOfPlace) {
        Set<Ticket> reserveTicket = new HashSet<>();
        for (int i = 0; i < amountOfPlace; i++) {
            Ticket ticket = freeTicket.get(i);
            ticket.setFree(false);
            reserveTicket.add(ticket);
        }

        Oder order = Oder.builder()
                .customer(customer)
                .build();
        balanceProcessor.byuTicket(event.getEventAmount().getId(), customer.getAmount().getId(), coastOfTicket(reserveTicket));
        return saveTicketWithOrder(order, reserveTicket);

    }

    @Transactional
    public Oder createOrder(Event event, Customer customer, List<Integer> numberOfPlace) {
        List<Ticket> ticketByNumberOfPlace = ticketService.getTicketByNumberOfPlace(numberOfPlace);
        BigDecimal totalCoast = BigDecimal.ZERO;

        for (Ticket ticket : ticketByNumberOfPlace) {
            ticket.setFree(false);
            totalCoast = totalCoast.add(ticket.getCoast());
        }

        Oder order = Oder.builder()
                .customer(customer)
                .build();

        balanceProcessor.byuTicket(event.getEventAmount().getId(), customer.getAmount().getId(), totalCoast);
        return saveTicketWithOrder(order, ticketByNumberOfPlace);

    }

    @Transactional
    public void refuseOrder(Long ticketId ) {
        Customer customer = customerService.findByTicketId(ticketId);
        Ticket ticket = ticketService.findOne(ticketId);
        Event event = ticket.getEvent();
        Oder order = orderRepository.findOderByCustomerId(customer.getId());
        order.removeTicket(ticket);
        ticket.setFree(true);
        balanceProcessor.byuTicket(customer.getAmount().getId(),  event.getEventAmount().getId(), ticket.getCoast());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Oder saveTicketWithOrder(Oder oder, Collection<Ticket> reserveTicket) {
        orderRepository.save(oder);
        reserveTicket.forEach(ticket -> {
            oder.addTicket(ticket);
            ticketService.saveTicket(ticket);
        });
        return oder;
    }

    private BigDecimal coastOfTicket(Set<Ticket> reserveTicket) {
        BigDecimal result = BigDecimal.ZERO;
        for (Ticket ticket : reserveTicket) {
            result = result.add(ticket.getCoast());
        }
        return result;
    }

    public Oder findOderByCustomerId (Long customerId) {
        return orderRepository.findOderByCustomerId(customerId);
    }

    @Transactional
    public void refuseOrder(Customer customer, Ticket ticket, Oder order) {
        Event event = ticket.getEvent();
        ticket.setFree(true);
        ticket.setOder(null);
        balanceProcessor.returnTicket(customer.getAmount().getId(),  event.getEventAmount().getId(), ticket.getCoast());
    }
}
