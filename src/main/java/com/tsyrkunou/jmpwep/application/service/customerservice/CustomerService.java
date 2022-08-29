package com.tsyrkunou.jmpwep.application.service.customerservice;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.amounts.customerbalance.Amount;
import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerSearch customerSearch;

    @BatchSize(size = 4)
    @Transactional
    public Customer createCustomer(CustomerData customerData) {
        Amount amount = new Amount();
        if (customerData.getBalance() != null) {
            amount.setBalance(customerData.getBalance());
        } else {
            amount.setBalance(BigDecimal.ZERO);
        }

        Customer customer = Customer.builder()
                .name(customerData.getName())
                .amount(amount)
                .build();
        Customer customer1 = Customer.builder()
                .name(customerData.getName() + "1")
                .amount(amount)
                .build();
        Customer customer2 = Customer.builder()
                .name(customerData.getName() + "2")
                .amount(amount)
                .build();
        Customer customer4 = Customer.builder()
                .name(customerData.getName() + "3")
                .amount(amount)
                .build();
        List<Customer> customers = List.of(customer1, customer2, customer, customer4);
        for (Customer customer3 : customers) {
            customerRepository.save(customer3);
        }
        return customer;
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
