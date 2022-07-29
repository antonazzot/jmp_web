package com.tsyrkunou.jmpwep.application.converter;

import org.mapstruct.Mapper;

import com.tsyrkunou.jmpwep.application.model.customer.CreateCustomerRequest;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.model.event.CreateEventRequest;
import com.tsyrkunou.jmpwep.application.model.event.EventData;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderData;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderRequest;

@Mapper(componentModel = "spring")
public interface ApplicationConverter {
    CustomerData convert(CreateCustomerRequest request);

    EventData convert(CreateEventRequest request);

    CreateOrderData convert(CreateOrderRequest request);
}
