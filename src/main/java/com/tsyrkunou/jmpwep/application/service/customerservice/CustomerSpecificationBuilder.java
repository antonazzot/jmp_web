package com.tsyrkunou.jmpwep.application.service.customerservice;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.order.Oder;

import lombok.RequiredArgsConstructor;
import static com.tsyrkunou.jmpwep.application.utils.SpecificationUtils.isFieldEqualsTo;

import javax.persistence.criteria.JoinType;

@Component
@RequiredArgsConstructor
public class CustomerSpecificationBuilder<T extends Customer> {

    public Specification<T> buildForOne(Long id) {
        return isFieldEqualsTo(Customer.Fields.id, id);
    }

    public Specification<T> buildForOne(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Customer.Fields.name), name);
    }

    public Specification<T> buildForOneByTicketId(Long ticketId) {
        return Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join(Customer.Fields.oders, JoinType.LEFT)
                        .join(Oder.Fields.tickets, JoinType.LEFT)
                        .get("id"), ticketId));
    }
}
