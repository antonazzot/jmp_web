package com.tsyrkunou.jmpwep.application.controller.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;

import org.springframework.hateoas.MediaTypes;

import com.tsyrkunou.jmpwep.application.model.customer.CreateCustomerRequest;
import com.tsyrkunou.jmpwep.application.model.event.CreateEventRequest;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerResponse;
import com.tsyrkunou.jmpwep.application.model.event.EventResponse;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderRequest;
import com.tsyrkunou.jmpwep.application.model.order.OrderResponse;

@RequestMapping(value = "/jmp")
public interface ApplicationRest {

    @Operation(description = "Create user")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request);

    @Operation(description = "Get user by id")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<CustomerResponse> getUserById (@PathVariable(name = "id") Long id);

    @Operation(description = "Create event")
    @PostMapping(value = "/event", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request);

    @Operation(description = "Create oder")
    @PostMapping(value = "/oder", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request);

}
