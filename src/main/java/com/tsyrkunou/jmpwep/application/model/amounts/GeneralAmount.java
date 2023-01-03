package com.tsyrkunou.jmpwep.application.model.amounts;

import java.math.BigDecimal;
import java.util.Map;

public interface GeneralAmount {
    void depositOnAmount(BigDecimal bigDecimal);

    void deductFromAmount(BigDecimal bigDecimal);

    Map<String, String> watchBalance();
}
