package com.tsyrkunou.jmpwep.application.model.event;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EventData {
    private String name;
    private Integer amountOfPlace;
    private BigDecimal coastOfTicket;
}
