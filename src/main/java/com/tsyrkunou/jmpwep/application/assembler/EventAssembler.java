package com.tsyrkunou.jmpwep.application.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.controller.restcontroller.ApplicationRestImpl;
import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.event.EventResponse;

@Component
public class EventAssembler implements RepresentationModelAssembler<Event, EventResponse> {

    @Override
    public EventResponse toModel(Event entity) {
        var model = new EventResponse();

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setTicket(entity.getTicket());

        model.add(linkTo(methodOn(ApplicationRestImpl.class)
                .getUserById(entity.getId()))
                .withSelfRel());
        return model;
    }

}