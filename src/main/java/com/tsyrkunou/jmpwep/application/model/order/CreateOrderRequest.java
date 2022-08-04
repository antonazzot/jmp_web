package com.tsyrkunou.jmpwep.application.model.order;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Size;

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
public class CreateOrderRequest {

    @Size(min = 3, max = 40)
    @Schema(description = "Customer name")
    private String customerName;

    @Schema(description = "Customer id")
    private Long customerId;

    @Schema(description = "Customer balance")
    private BigDecimal customerBalance;

    @Schema(description = "True if oder made by new customer")
    private Boolean newCustomer;

    @Size(min = 3, max = 100)
    @Schema(description = "Event name")
    private String eventName;

    @Schema(description = "Customer id")
    private Long eventId;

    @Schema(description = "Amount of place would like to oder")
    private Integer amountOfPlace;

    @Schema(description = "Number of place would like to oder")
    private List<Integer> numberOfPlace;

}
