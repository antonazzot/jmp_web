package com.tsyrkunou.jmpwep.application.model.event;

import java.math.BigDecimal;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.hateoas.RepresentationModel;

import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse extends RepresentationModel<EventResponse> {
    private Long id;
    private String name;
    private Set<Ticket> ticket;
    private BigDecimal balance;
}
