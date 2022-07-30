package com.tsyrkunou.jmpwep.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.tsyrkunou.jmpwep.application.model.customerbalance.Amount;
import com.tsyrkunou.jmpwep.application.repository.AmountRepository;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;

@Service
@RequiredArgsConstructor
public class AmountService {
    private final AmountRepository amountRepository;

    public Amount findOne(Long amountId) {
        return amountRepository.findById(amountId)
                .orElseThrow(() -> new NotFoundException("Amount with id: d" + amountId + " not found"));
    }

    public Amount save(Amount deductAmount) {
       return amountRepository.save(deductAmount);
    }
}
