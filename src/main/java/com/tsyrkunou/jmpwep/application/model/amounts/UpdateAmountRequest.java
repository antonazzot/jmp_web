package com.tsyrkunou.jmpwep.application.model.amounts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAmountRequest {
    @Size(min = 3, max = 40)
    @Schema(description = "Customer name")
    private String customerName;

    @Size(min = 3, max = 40)
    @Schema(description = "Customer id")
    private Long customerId;

    @Size(min = 3, max = 40)
    @Schema(description = "Event name")
    private String eventName;

    @Size(min = 3, max = 40)
    @Schema(description = "Event id")
    private Long eventId;

    @Schema(description = "Deposit or withdraw sum", required = true)
    private BigDecimal balance;
}
