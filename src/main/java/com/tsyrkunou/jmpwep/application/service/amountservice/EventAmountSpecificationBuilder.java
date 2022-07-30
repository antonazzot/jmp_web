package com.tsyrkunou.jmpwep.application.service.amountservice;

import static com.tsyrkunou.jmpwep.application.utils.SpecificationUtils.isFieldEqualsTo;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.model.amounts.eventbalance.EventAmount;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventAmountSpecificationBuilder<T extends EventAmount> {

    public Specification<T> buildForOne(Long id) {
        return isFieldEqualsTo(EventAmount.Fields.id, id);
    }

    public Specification<T> buildForOne(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(EventAmount.Fields.event, JoinType.LEFT)
                        .get("name"), name);
    }
}
