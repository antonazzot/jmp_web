package com.tsyrkunou.jmpwep.application.repository;

import java.util.Optional;

import com.tsyrkunou.jmpwep.application.model.event.Event;

public interface EventRepository extends JpaAndSpecificationRepository<Event, Long> {
    Optional<Event> findEventByName(String name);
}
