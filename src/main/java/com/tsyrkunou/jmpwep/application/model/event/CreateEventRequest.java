package com.tsyrkunou.jmpwep.application.model.event;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventRequest {

    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Event name", required = true)
    private String name;

    @PositiveOrZero
    @Schema(description = "Amount of place", required = true)
    private Integer amountOfPlace;

    @PositiveOrZero
    @Schema(description = "Coast of ticket")
    private BigDecimal coastOfTicket;

    @PositiveOrZero
    @Schema(description = "Event balance")
    private BigDecimal balance;


}
