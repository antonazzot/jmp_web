package com.tsyrkunou.jmpwep.application.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.repository.TicketRepository;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TicketSearch {
    private final TicketRepository ticketRepository;
    private final TicketSpecificationBuilder<Ticket> specificationBuilder;

    public Ticket findOne(Long id) {
        var specification = specificationBuilder.buildForOne(id);
        return ticketRepository.findOne(specification)
                .orElseThrow(() -> new NotFoundException("ticket.not-found" + id));
    }

    public Ticket findOne(Integer placeNumber) {
        var specification = specificationBuilder.buildForOne(placeNumber);
        return ticketRepository.findOne(specification)
                .orElseThrow(() -> new NotFoundException("ticket.with.place.number.not-found" + placeNumber));
    }

    public List<Ticket> findFreeTicketWithEvent(Event event) {
        var specification = specificationBuilder.buildFreeForEvent(event);
        return ticketRepository.findAll(specification);
    }

    public List<Ticket> findBockedTicketByUser(Long id) {
        var specification = specificationBuilder.buildBockedTicket(id);
        return ticketRepository.findAll(specification);
    }

    public List <Ticket> findFreeTicketByNumberOfPlace(List<Integer> numberOfPlace) {
        Specification<Ticket> specification = specificationBuilder.buildFreeByNumberOfPlace(numberOfPlace);
        return ticketRepository.findAll(specification);
    }
}
