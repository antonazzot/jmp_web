package com.tsyrkunou.jmpwep.application.model.amounts;

import java.math.BigDecimal;

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
public class UpdateAmountData {
    private String customerName;
    private Long customerId;
    private String eventName;
    private Long eventId;
    private BigDecimal balance;
}
