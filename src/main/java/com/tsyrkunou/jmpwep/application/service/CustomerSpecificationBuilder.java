package com.tsyrkunou.jmpwep.application.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;

import lombok.RequiredArgsConstructor;
import static com.tsyrkunou.jmpwep.application.utils.SpecificationUtils.isFieldEqualsTo;

@Component
@RequiredArgsConstructor
public class CustomerSpecificationBuilder<T extends Customer> {

    public Specification<T> buildForOne(Long id) {
        return isFieldEqualsTo(Customer.Fields.id, id);
    }

    public Specification<T> buildForOne(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Customer.Fields.name), name);
    }
}
