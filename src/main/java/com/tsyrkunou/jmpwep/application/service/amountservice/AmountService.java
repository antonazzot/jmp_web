package com.tsyrkunou.jmpwep.application.service.amountservice;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.tsyrkunou.jmpwep.application.model.amounts.customerbalance.Amount;
import com.tsyrkunou.jmpwep.application.repository.AmountRepository;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;

@Service
@RequiredArgsConstructor
public class AmountService {
    private final AmountRepository amountRepository;
    private final AmountSpecificationBuilder<Amount> amountAmountSpecificationBuilder;

    public Amount findOne(Long amountId) {
        return amountRepository.findById(amountId)
                .orElseThrow(() -> new NotFoundException("Amount with id: d" + amountId + " not found"));
    }

    public Amount findOne(String customerName) {
        var specification = amountAmountSpecificationBuilder.buildForOne(customerName);
        return amountRepository.findOne(specification)
                .orElseThrow(() -> new NotFoundException("Amount by customer name: " + customerName + " not found"));
    }

    public Amount save(Amount deductAmount) {
        return amountRepository.save(deductAmount);
    }

}
