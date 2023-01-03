package com.tsyrkunou.jmpwep.application.service.amountservice;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.amounts.GeneralAmount;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceProcessor<T extends GeneralAmount, I extends GeneralAmount> {

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void byuTicket(T depositAmount, I deductFromAmount, BigDecimal bigDecimal) throws RuntimeException {
        exchange(depositAmount, deductFromAmount, bigDecimal);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void returnTicket(T depositAmount, I deductFromAmount, BigDecimal bigDecimal) {
        exchange(depositAmount, deductFromAmount, bigDecimal);
    }

    private void exchange(T depositAmount, I deductFromAmount, BigDecimal bigDecimal) {
        deductFromAmount.deductFromAmount(bigDecimal);
        depositAmount.depositOnAmount(bigDecimal);
    }
}
