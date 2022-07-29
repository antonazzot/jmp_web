package com.tsyrkunou.jmpwep.application.service;

import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerSearch {
    private final CustomerRepository customerRepository;
    private final CustomerSpecificationBuilder<Customer> specificationBuilder;

    public Customer findOne(Long id) {
        var specification = specificationBuilder.buildForOne(id);
        return customerRepository.findOne(specification)
                .orElseThrow(() -> new NotFoundException("Customer with id: " + id + " not found"));
    }

    public Customer findOne(String name) {
        var specification = specificationBuilder.buildForOne(name);
        return customerRepository.findOne(specification)
                .orElseThrow(() -> new NotFoundException("Customer with name: " + name + " not found"));
    }

}
