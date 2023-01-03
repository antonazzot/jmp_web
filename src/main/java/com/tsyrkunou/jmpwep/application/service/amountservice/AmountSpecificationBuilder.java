package com.tsyrkunou.jmpwep.application.service.amountservice;

import static com.tsyrkunou.jmpwep.application.utils.SpecificationUtils.isFieldEqualsTo;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.model.amounts.customerbalance.Amount;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AmountSpecificationBuilder<T extends Amount> {

    public Specification<T> buildForOne(Long id) {
        return isFieldEqualsTo(Amount.Fields.id, id);
    }

    public Specification<T> buildForOne(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(Amount.Fields.customer, JoinType.LEFT)
                        .get("name"), name);
    }
}
