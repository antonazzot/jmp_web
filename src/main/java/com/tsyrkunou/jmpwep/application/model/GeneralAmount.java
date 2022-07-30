package com.tsyrkunou.jmpwep.application.model;

import java.math.BigDecimal;

public interface GeneralAmount {
    void depositOnAmount (BigDecimal bigDecimal);
    void deductFromAmount (BigDecimal bigDecimal);
}
