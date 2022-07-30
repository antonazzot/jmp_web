package com.tsyrkunou.jmpwep.application.service;

import java.math.BigDecimal;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.model.customerbalance.Amount;
import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerSearch customerSearch;

    @Transactional
    public Customer createCustomer(CustomerData customerData) {
        Amount amount = new Amount();
        if (customerData.getBalance() != null )
            amount.setBalance(customerData.getBalance());
        else amount.setBalance(BigDecimal.ZERO);

        Customer customer = Customer.builder()
                .name(customerData.getName())
                .amount(amount)
                .build();
        return customerRepository.save(customer);
    }

    public Customer findOne(Long id) {
        return customerSearch.findOne(id);
    }

    public Customer findOne(String name) {
        return customerSearch.findOne(name);
    }

    public Customer findByTicketId(Long ticketId) {
        return customerSearch.findByTicketId(ticketId);
    }



    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
