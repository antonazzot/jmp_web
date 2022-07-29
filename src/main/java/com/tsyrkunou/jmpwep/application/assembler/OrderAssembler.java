package com.tsyrkunou.jmpwep.application.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.controller.restcontroller.ApplicationRestImpl;
import com.tsyrkunou.jmpwep.application.model.order.Oder;
import com.tsyrkunou.jmpwep.application.model.order.OrderResponse;

@Component
public class OrderAssembler implements RepresentationModelAssembler<Oder, OrderResponse> {

    @Override
    public OrderResponse toModel(Oder entity) {
        var model = new OrderResponse();

        model.setId(entity.getId());
        model.setTickets(entity.getTickets());
        model.setEventName(entity.getTickets().stream().findFirst().orElseThrow().getEvent().getName());

        model.add(linkTo(methodOn(ApplicationRestImpl.class)
                .getUserById(entity.getId()))
                .withSelfRel());
        return model;
    }

}