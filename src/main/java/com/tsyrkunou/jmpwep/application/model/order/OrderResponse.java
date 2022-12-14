package com.tsyrkunou.jmpwep.application.model.order;

import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse extends RepresentationModel<OrderResponse> {
    private Long id;
    private String eventName;
    private Set<Ticket> tickets;
}
