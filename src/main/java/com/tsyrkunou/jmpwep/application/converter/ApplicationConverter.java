package com.tsyrkunou.jmpwep.application.converter;

import org.mapstruct.Mapper;

import com.tsyrkunou.jmpwep.application.model.customer.CreateCustomerRequest;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.model.amounts.UpdateAmountData;
import com.tsyrkunou.jmpwep.application.model.amounts.UpdateAmountRequest;
import com.tsyrkunou.jmpwep.application.model.event.CreateEventRequest;
import com.tsyrkunou.jmpwep.application.model.event.EventData;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderData;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderRequest;
import com.tsyrkunou.jmpwep.application.model.ticket.ReturnTicketData;
import com.tsyrkunou.jmpwep.application.model.ticket.ReturnTicketRequest;

@Mapper(componentModel = "spring")
public interface ApplicationConverter {
    CustomerData convert(CreateCustomerRequest request);

    EventData convert(CreateEventRequest request);

    CreateOrderData convert(CreateOrderRequest request);

    ReturnTicketData convert (ReturnTicketRequest request);

    UpdateAmountData convert (UpdateAmountRequest request);
}
