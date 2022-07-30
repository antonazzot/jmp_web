package com.tsyrkunou.jmpwep.application.service.amountservice;

import org.springframework.stereotype.Service;

import com.tsyrkunou.jmpwep.application.model.amounts.eventbalance.EventAmount;
import com.tsyrkunou.jmpwep.application.repository.EventAmountRepository;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventAmountService {
    private final EventAmountRepository eventAmountRepository;
    private final EventAmountSpecificationBuilder <EventAmount> eventAmountSpecificationBuilder;

    public EventAmount findOne(Long amountId) {
        return eventAmountRepository.findById(amountId)
                .orElseThrow(() -> new NotFoundException("Event amount with id: " + amountId + " not found"));
    }

    public EventAmount findOne(String eventAmount) {
        var specification = eventAmountSpecificationBuilder.buildForOne(eventAmount);
        return eventAmountRepository.findOne(specification)
                .orElseThrow(() -> new NotFoundException("Event amount by event name: " + eventAmount + " not found"));
    }


    public EventAmount save(EventAmount depositAmount) {
        return eventAmountRepository.save(depositAmount);
    }
}
