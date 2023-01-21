package com.tsyrkunou.jmpwep.application.service.customerservice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.config.BotConfig;
import com.tsyrkunou.jmpwep.application.model.ModelEntity;
import com.tsyrkunou.jmpwep.application.model.amounts.customerbalance.Amount;
import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerSearch customerSearch;
    private final BotConfig botConfig;

    @BatchSize(size = 4)
    @Transactional
    public Customer createCustomer(CustomerData customerData) {
//        String botOwn = botConfig.getBotOwner();
//        String botName = botConfig.getBotName();

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
//        Customer customer1 = Customer.builder()
//                .name(customerData.getName() + "1")
//                .amount(amount)
//                .build();
//        Customer customer2 = Customer.builder()
//                .name(customerData.getName() + "2")
//                .amount(amount)
//                .build();
//        Customer customer4 = Customer.builder()
//                .name(customerData.getName() + "3")
//                .amount(amount)
//                .build();
//        List<Customer> customers = List.of(customer1, customer2, customer, customer4);
        customerRepository.save(customer);

//        saveAllCustomers(customers);

        return customer;
    }

    private void saveAllCustomers(List<Customer> customers) {
        List<Customer> customers1 = customers;
        for (int i = 0; i < customers1.size(); i++) {
            customerRepository.save(customers1.get(i));
        }
    }

    private <T extends ModelEntity> ModelEntity getModell(String s) {
        Map<String, ModelEntity> modelEntityMap = Map.of("t", new Ticket(), "c", new Customer());
        return modelEntityMap.get(s);
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
