package com.tsyrkunou.jmpwep.application.service.eventservice;

import java.math.BigDecimal;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.amounts.eventbalance.EventAmount;
import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.event.EventData;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.repository.EventRepository;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;
import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.MyAppException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;

    @BatchSize(size = 4)
    @Transactional
    public Event createEvent(EventData eventData) {
        EventAmount amount = new EventAmount();
        if (eventData.getBalance() != null) {
            amount.setBalance(eventData.getBalance());
        } else {
            amount.setBalance(BigDecimal.ZERO);
        }

        Event event = new Event();
        event.setName(eventData.getName());
        event = processCreateEvent(event, eventData, amount);
        return eventRepository.findById(event.getId()).orElseThrow(() -> new MyAppException("Event not found"));
    }

    @Transactional
    @EntityGraph(value = "tickets")
    public Event findOne(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id" + eventId + "not found"));
    }

    @Transactional
    public Event findOne(String eventName) {
        return eventRepository.findEventByName(eventName)
                .orElseThrow(() -> new NotFoundException("Event with name" + eventName + "not found"));
    }

    private Event processCreateEvent(Event event, EventData eventData,
                                     EventAmount amount) {
        event.setEventAmount(amount);
        for (int i = 1; i < eventData.getAmountOfPlace() + 1; i++) {
            Ticket ticket = Ticket.builder()
                    .isFree(true)
                    .placeNumber(i)
                    .event(event)
                    .build();
            if (eventData.getCoastOfTicket() != null) {
                ticket.setCoast(eventData.getCoastOfTicket());
            }
            event.addTicket(ticket);
        }
        event = eventRepository.save(event);
        return event;
    }

    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }
}
