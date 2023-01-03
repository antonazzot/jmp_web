package com.tsyrkunou.jmpwep.application.model.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnTicketRequest {
    @Schema(description = "Ticket id", required = true)
    private Long ticketId;
}
