package com.tsyrkunou.jmpwep.application.model.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnTicketResponse extends RepresentationModel<ReturnTicketResponse> {
    private Long ticketId;
    private boolean ticketStatus;
    private String eventName;
    private Long amountId;
}
