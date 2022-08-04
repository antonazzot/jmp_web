package com.tsyrkunou.jmpwep.application.service.amountservice;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.amounts.GeneralAmount;
import com.tsyrkunou.jmpwep.application.model.amounts.customerbalance.Amount;
import com.tsyrkunou.jmpwep.application.model.amounts.eventbalance.EventAmount;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceProcessor<T extends GeneralAmount, I extends GeneralAmount> {
    private final AmountService amountService;
    private final EventAmountService eventAmountService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void byuTicket(Long depositAmountId, Long deductFromAmountId, BigDecimal bigDecimal) {
        EventAmount depositAmount = eventAmountService.findOne(depositAmountId);
        Amount deductAmount = amountService.findOne(deductFromAmountId);
        exchange((T) depositAmount, (I) deductAmount, bigDecimal);
        amountService.save(deductAmount);
        eventAmountService.save(depositAmount);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void returnTicket(Long depositAmountId, Long deductFromAmountId, BigDecimal bigDecimal) {
        EventAmount deductAmount = eventAmountService.findOne(deductFromAmountId);
        Amount depositAmount = amountService.findOne(depositAmountId);
        exchange((T) depositAmount, (I) deductAmount, bigDecimal);
        amountService.save(depositAmount);
        eventAmountService.save(deductAmount);
    }

    private void exchange(T depositAmount, I deductFromAmount, BigDecimal bigDecimal) {
        deductFromAmount.deductFromAmount(bigDecimal);
        depositAmount.depositOnAmount(bigDecimal);
    }

}
