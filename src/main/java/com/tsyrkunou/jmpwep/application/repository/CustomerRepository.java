package com.tsyrkunou.jmpwep.application.repository;

import java.util.Optional;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;

public interface CustomerRepository extends JpaAndSpecificationRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
