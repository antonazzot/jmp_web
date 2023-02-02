package com.tsyrkunou.jmpwep.application.controller.restcontroller;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import javax.validation.Valid;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tsyrkunou.jmpwep.application.model.amounts.UpdateAmountRequest;
import com.tsyrkunou.jmpwep.application.model.amounts.UpdateAmountResponse;
import com.tsyrkunou.jmpwep.application.model.customer.CreateCustomerRequest;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerResponse;
import com.tsyrkunou.jmpwep.application.model.event.CreateEventRequest;
import com.tsyrkunou.jmpwep.application.model.event.EventResponse;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderRequest;
import com.tsyrkunou.jmpwep.application.model.order.OrderResponse;
import com.tsyrkunou.jmpwep.application.model.ticket.ReturnTicketRequest;
import com.tsyrkunou.jmpwep.application.model.ticket.ReturnTicketResponse;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping(value = "/jmp")
public interface ApplicationRest {

    @Operation(description = "Create customer")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request);

    @Operation(description = "Redirect")
    @GetMapping(value = "/hello", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<String> hello();

    @GetMapping(value = "/img", produces = IMAGE_JPEG_VALUE)
    byte[] image(@RequestParam("name") String name);

    @Operation(description = "Get user by id")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<CustomerResponse> getUserById(@PathVariable(name = "id") Long id);

    @Operation(description = "Create event")
    @PostMapping(value = "/event", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request);

    @Operation(description = "Delete event")
    @DeleteMapping(value = "/event/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<?> deleteEvent(@PathVariable(name = "id") Long id);

    @Operation(description = "Create oder")
    @PostMapping(value = "/oder", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request);

    @Operation(description = "Return ticket")
    @PatchMapping(value = "/ticket", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<ReturnTicketResponse> returnTicket(@Valid @RequestBody ReturnTicketRequest request);

    @Operation(description = "Update amount")
    @PutMapping(value = "/amount", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<UpdateAmountResponse> updateAmount(@Valid @RequestBody UpdateAmountRequest request);

    @PostMapping(value = "updatebot")
    ResponseEntity<Void> updateBot(@RequestParam String botName, @RequestParam String botOwner);

}
