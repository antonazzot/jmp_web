package com.tsyrkunou.jmpwep.application.model.customer;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.tsyrkunou.jmpwep.application.model.order.Oder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse extends RepresentationModel<CustomerResponse> {
    private Long id;
    private String name;
    private BigDecimal balance;
    private Set<Oder> oders;
}
