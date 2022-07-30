package com.tsyrkunou.jmpwep.application.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerSearch {
    public static final String NOT_FOUND = " not found";
    private final CustomerRepository customerRepository;
    private final CustomerSpecificationBuilder<Customer> specificationBuilder;

    public Customer findOne(Long id) {
        var specification = specificationBuilder.buildForOne(id);
        return customerRepository.findOne(specification)
                .orElseThrow(() -> new NotFoundException("Customer with id: " + id + NOT_FOUND));
    }

    public Customer findOne(String name) {
        var specification = specificationBuilder.buildForOne(name);
        return customerRepository.findOne(specification)
                .orElseThrow(() -> new NotFoundException("Customer with name: " + name + NOT_FOUND));
    }

    public Customer findByTicketId(Long ticketId) {
        Specification<Customer> customerSpecification = specificationBuilder.buildForOneByTicketId(ticketId);
        return customerRepository.findOne(customerSpecification)
                .orElseThrow(() -> new NotFoundException("Customer with ticket id: " + ticketId + NOT_FOUND));
    }
}
