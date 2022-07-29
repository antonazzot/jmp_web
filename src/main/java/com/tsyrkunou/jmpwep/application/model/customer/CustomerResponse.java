package com.tsyrkunou.jmpwep.application.model.customer;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.hateoas.RepresentationModel;

import com.tsyrkunou.jmpwep.application.model.order.Oder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse extends RepresentationModel<CustomerResponse> {
    private Long id;
    private String name;
    private Set<Oder> oders;
}
