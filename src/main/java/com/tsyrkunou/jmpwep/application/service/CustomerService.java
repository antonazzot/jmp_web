package com.tsyrkunou.jmpwep.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerSearch customerSearch;

    @Transactional
    public Customer createCustomer(CustomerData customerData) {
        Customer customer = Customer.builder()
                .name(customerData.getName())
                .balance(customerData.getBalance()).build();
        return customerRepository.save(customer);
    }

    public Customer findOne(Long id) {
        return customerSearch.findOne(id);
    }

    public Customer findOne(String name) {
        return customerSearch.findOne(name);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
