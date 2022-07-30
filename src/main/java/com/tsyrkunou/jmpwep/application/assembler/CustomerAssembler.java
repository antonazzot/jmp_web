package com.tsyrkunou.jmpwep.application.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.controller.restcontroller.ApplicationRestImpl;
import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerResponse;

@Component
public class CustomerAssembler implements RepresentationModelAssembler<Customer, CustomerResponse> {

    @Override
    public CustomerResponse toModel(Customer entity) {
        var model = new CustomerResponse();

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setOders(entity.getOders());
        model.setBalance(entity.getAmount().getBalance());

        model.add(linkTo(methodOn(ApplicationRestImpl.class)
                .getUserById(entity.getId()))
                .withSelfRel());
        return model;
    }

}