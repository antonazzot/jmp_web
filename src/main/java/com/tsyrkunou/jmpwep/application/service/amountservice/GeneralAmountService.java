package com.tsyrkunou.jmpwep.application.service.amountservice;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.amounts.GeneralAmount;
import com.tsyrkunou.jmpwep.application.model.amounts.UpdateAmountData;
import com.tsyrkunou.jmpwep.application.model.amounts.UpdateAmountResponse;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;
import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.MyAppException;

@Service
@RequiredArgsConstructor
public class GeneralAmountService {
    private final AmountService amountService;
    private final EventAmountService eventAmountService;

    @Transactional
    public UpdateAmountResponse updateAmount(UpdateAmountData updateAmountData) {
        validateAmountData(updateAmountData);

        BigDecimal balance = updateAmountData.getBalance();
        GeneralAmount generalAmount = detectGeneralAmount(updateAmountData)
                .orElseThrow(() -> new NotFoundException("Required amount didn't found"));
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            depositOperation(generalAmount, balance);
        } else {
            withdrawOperation(generalAmount, balance);
        }

        return new UpdateAmountResponse(generalAmount.watchBalance().get("ownerName"),
                generalAmount.watchBalance().get("ownerBalance"));
    }

    private Optional<GeneralAmount> detectGeneralAmount(UpdateAmountData updateAmountData) {
        String eventName = updateAmountData.getEventName();
        String customerName = updateAmountData.getCustomerName();
        Long customerId = updateAmountData.getCustomerId();
        Long eventId = updateAmountData.getEventId();
        GeneralAmount generalAmount = null;
        if (eventName == null && customerName == null && customerId == null) {
            generalAmount = eventAmountService.findOne(eventId);
        } else if (eventName == null && customerName == null && eventId == null) {
            generalAmount = amountService.findOne(customerId);
        } else if (eventName == null && eventId == null && customerId == null) {
            generalAmount = amountService.findOne(customerName);
        } else if (customerName == null && eventId == null && customerId == null) {
            generalAmount = eventAmountService.findOne(eventName);
        }
        return Optional.ofNullable(generalAmount);
    }

    private void withdrawOperation(GeneralAmount generalAmount, BigDecimal sum) {
        generalAmount.deductFromAmount(sum.abs());
    }

    private void depositOperation(GeneralAmount generalAmount, BigDecimal sum) {
        generalAmount.depositOnAmount(sum);
    }

    private void validateAmountData(UpdateAmountData updateAmountData) {
        String eventName = updateAmountData.getEventName();
        String customerName = updateAmountData.getCustomerName();
        Long customerId = updateAmountData.getCustomerId();
        Long eventId = updateAmountData.getEventId();

        if ((customerId == null && customerName == null) && (eventName == null && eventId == null)) {
            throw new MyAppException("Require data doesn't present");
        }

        if ((customerId != null && customerName != null) || (eventName != null && eventId != null)
                || (customerId != null && eventName != null) || (customerName != null && eventId != null)
                || (customerName != null && eventName != null) || (customerId != null && eventId != null)) {
            throw new MyAppException("Present extra data");
        }
    }
}
