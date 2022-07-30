package com.tsyrkunou.jmpwep.application.service;

import static com.tsyrkunou.jmpwep.application.utils.SpecificationUtils.isFieldEqualsTo;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.order.Oder;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.persistence.criteria.JoinType;

@Component
@RequiredArgsConstructor
public class TicketSpecificationBuilder<T extends Ticket> {

    public Specification<T> buildForOne(Long id) {
        return isFieldEqualsTo(Ticket.Fields.id, id);
    }

    public Specification<T> buildForOne(Integer placeNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Ticket.Fields.placeNumber),
                placeNumber);
    }

    public Specification<T> buildFreeForEvent(Event event) {
        Specification<T> one = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Ticket.Fields.event), event.getId()));
        Specification<T> two =
                Specification.where((root, query, criteriaBuilder) -> root.get(Ticket.Fields.isFree).in(true));
        return Specification.where(one).and(two);
    }

    public Specification<T> buildBockedTicket(Long id) {
        Specification<T> one = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join(Ticket.Fields.oder, JoinType.LEFT)
                        .join(Oder.Fields.customer, JoinType.LEFT)
                        .get("id"), id));
        Specification<T> two =
                Specification.where((root, query, criteriaBuilder) -> root.get(Ticket.Fields.isFree).in(false));
        return Specification.where(one).and(two);
    }

    public Specification <T> buildFreeByNumberOfPlace(List<Integer> numberOfPlace) {
        Specification<T> one = Specification.where((root, query, criteriaBuilder) ->
                root.get("id").in(numberOfPlace));
        Specification<T> two =
                Specification.where((root, query, criteriaBuilder) -> root.get(Ticket.Fields.isFree).in(true));
        return Specification.where(one).and(two);
    }
}
