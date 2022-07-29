package com.tsyrkunou.jmpwep.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tsyrkunou.jmpwep.application.AbstractTest;
import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.repository.EventRepository;

class EventServiceTest extends AbstractTest {

    private String eventName = "TestEventName";

    private Customer customer;
    private Event event;

    @Autowired
    private EventRepository eventRepo;

    @BeforeEach
    private void init () {
        eventRepo.deleteAll();
    }

    @Test
    void createEvent() {
        assertThat(eventRepo.findAll()).isEmpty();
        event = Event.builder()
                .name(eventName)
                .build();
        eventRepo.save(event);
        assertThat(eventRepo.findAll()).hasSize(1);
        assertThat(eventRepo.findEventByName(eventName)).isNotEmpty();
    }

    @Test
    void createEventReturnEvent() {
        assertThat(eventRepo.findAll()).isEmpty();
        event = Event.builder()
                .name(eventName)
                .build();
        Event event = eventRepo.save(this.event);
        assertThat(eventRepo.findAll()).hasSize(1);
        assertThat(eventRepo.findById(event.getId())).contains(event);
        Optional<Event> byId = eventRepo.findById(2L);
    }

    @Test
    void findEventByName() {
        assertThat(eventRepo.findAll()).isEmpty();
        event = Event.builder()
                .name(eventName)
                .build();
        eventRepo.save(event);
        assertThat(eventRepo.findAll()).hasSize(1);
        Optional<Event> eventByName1 = eventRepo.findEventByName(eventName);
        assertThat(eventByName1.get().getName()).isEqualTo(eventName);
    }
}