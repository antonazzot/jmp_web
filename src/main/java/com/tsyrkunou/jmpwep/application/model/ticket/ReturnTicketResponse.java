package com.tsyrkunou.jmpwep.application.model.ticket;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
