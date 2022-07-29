package com.tsyrkunou.jmpwep.application.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.order.Oder;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.repository.OrderRepository;
import com.tsyrkunou.jmpwep.application.repository.TicketRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public Oder createOrder(Event event, Customer customer, List<Ticket> freeTicket, Integer amountOfPlace) {
        Set<Ticket> reserveTicket = new HashSet<>();
        for (int i = 0; i < amountOfPlace; i++) {
            Ticket ticket = freeTicket.get(i);
            ticket.setFree(false);
            reserveTicket.add(ticket);
        }

        customer.setBalance(customer.getBalance().subtract(coastOfTicket(reserveTicket)));

        Oder order = Oder.builder()
                .customer(customer)
                .build();

        return saveTicketWithOrder(order, reserveTicket);

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Oder saveTicketWithOrder(Oder oder, Set<Ticket> reserveTicket) {
        orderRepository.save(oder);
        reserveTicket.forEach(ticket -> {
            oder.addTicket(ticket);
            ticketRepository.save(ticket);
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

    public Oder createSimpleOrder() {
        return orderRepository.save(new Oder());
    }
}
