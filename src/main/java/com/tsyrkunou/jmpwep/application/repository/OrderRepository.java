package com.tsyrkunou.jmpwep.application.repository;

import com.tsyrkunou.jmpwep.application.model.order.Oder;

public interface OrderRepository extends JpaAndSpecificationRepository<Oder, Long> {
    public Oder findOderByCustomerId(Long customerId);
}
