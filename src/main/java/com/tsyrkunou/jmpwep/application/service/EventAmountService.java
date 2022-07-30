package com.tsyrkunou.jmpwep.application.service;

import org.springframework.stereotype.Service;

import com.tsyrkunou.jmpwep.application.model.eventbalance.EventAmount;
import com.tsyrkunou.jmpwep.application.repository.EventAmountRepository;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventAmountService {
    private final EventAmountRepository eventAmountRepository;

    public EventAmount findOne(Long amountId) {
        return eventAmountRepository.findById(amountId)
                .orElseThrow(() -> new NotFoundException("Event amount with id: " + amountId + " not found"));
    }

    public EventAmount save(EventAmount depositAmount) {
        return eventAmountRepository.save(depositAmount);
    }
}
