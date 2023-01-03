package com.tsyrkunou.jmpwep.application.model.order;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateOrderData {
    private String customerName;
    private Long customerId;
    private Boolean newCustomer;
    private BigDecimal customerBalance;
    private String eventName;
    private Long eventId;
    private Integer amountOfPlace;
    private List<Integer> numberOfPlace;
}
