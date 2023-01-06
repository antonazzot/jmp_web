package com.tsyrkunou.jmpwep.application.repository;

import java.util.Optional;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;

import io.micrometer.core.annotation.Timed;

public interface CustomerRepository extends JpaAndSpecificationRepository<Customer, Long> {
    @Timed("email")
    Optional<Customer> findByEmail(String email);
}
