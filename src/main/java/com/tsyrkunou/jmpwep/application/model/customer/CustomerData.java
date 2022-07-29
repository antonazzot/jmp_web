package com.tsyrkunou.jmpwep.application.model.customer;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerData {
    private String name;
    private BigDecimal balance;
}
