package com.tsyrkunou.jmpwep.application.controller.restcontroller;

import lombok.RequiredArgsConstructor;

import static com.tsyrkunou.jmpwep.application.utils.ResourceUtils.buildLocationUri;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.tsyrkunou.jmpwep.application.assembler.CustomerAssembler;
import com.tsyrkunou.jmpwep.application.assembler.EventAssembler;
import com.tsyrkunou.jmpwep.application.assembler.OrderAssembler;
import com.tsyrkunou.jmpwep.application.converter.ApplicationConverter;
import com.tsyrkunou.jmpwep.application.model.customer.CreateCustomerRequest;
import com.tsyrkunou.jmpwep.application.model.event.CreateEventRequest;
import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerResponse;
import com.tsyrkunou.jmpwep.application.model.event.EventData;
import com.tsyrkunou.jmpwep.application.model.event.EventResponse;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderData;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderRequest;
import com.tsyrkunou.jmpwep.application.model.order.Oder;
import com.tsyrkunou.jmpwep.application.model.order.OrderResponse;
import com.tsyrkunou.jmpwep.application.model.ticket.ReturnTicketData;
import com.tsyrkunou.jmpwep.application.model.ticket.ReturnTicketRequest;
import com.tsyrkunou.jmpwep.application.model.ticket.ReturnTicketResponse;
import com.tsyrkunou.jmpwep.application.service.EventService;
import com.tsyrkunou.jmpwep.application.service.CustomerService;
import com.tsyrkunou.jmpwep.application.service.TicketBookingService;

@RequiredArgsConstructor
@RestController
public class ApplicationRestImpl implements ApplicationRest {
    private final ApplicationConverter applicationConverter;
    private final CustomerService customerService;
    private final EventService eventService;
    private final CustomerAssembler customerAssembler;
    private final EventAssembler eventAssembler;
    private final TicketBookingService ticketBookingService;
    private final OrderAssembler orderAssembler;

    @Override
    public ResponseEntity<CustomerResponse> create(CreateCustomerRequest request) {
        CustomerData customerData = applicationConverter.convert(request);
        Customer customer = customerService.createCustomer(customerData);
        return ResponseEntity.created(buildLocationUri(getClass(), customer.getId()))
                .body(customerAssembler.toModel(customer));
    }

    @Override
    public ResponseEntity<CustomerResponse> getUserById(Long id) {
        var user = customerService.findOne(id);
        return ResponseEntity.ok(customerAssembler.toModel(user));
    }

    @Override
    public ResponseEntity<EventResponse> createEvent(CreateEventRequest request) {
        EventData eventData = applicationConverter.convert(request);
        Event event = eventService.createEvent(eventData);
        return ResponseEntity.ok(eventAssembler.toModel(event));
    }

    @Override
    public ResponseEntity<OrderResponse> createOrder(CreateOrderRequest request) {
        CreateOrderData createOrderData = applicationConverter.convert(request);
        Oder oder = ticketBookingService.createOrder(createOrderData);
        return ResponseEntity.ok(orderAssembler.toModel(oder));
    }

    @Override
    public ResponseEntity<ReturnTicketResponse> returnTicket(ReturnTicketRequest request) {
        ReturnTicketData returnTicketData = applicationConverter.convert(request);
        ReturnTicketResponse returnTicketResponse = ticketBookingService.returnTicket(returnTicketData);
        return ResponseEntity.ok(returnTicketResponse);
    }

}
