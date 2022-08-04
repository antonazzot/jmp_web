package com.tsyrkunou.jmpwep.application.model.customer;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
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
public class CreateCustomerRequest {
    @NotBlank
    @Size(min = 3, max = 40)
    @Schema(description = "Customer name", required = true)
    private String name;

    @PositiveOrZero
    @Schema(description = "Customer balance", required = true)
    private BigDecimal balance;

}
