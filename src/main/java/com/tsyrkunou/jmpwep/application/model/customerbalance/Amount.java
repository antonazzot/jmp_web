package com.tsyrkunou.jmpwep.application.model.customerbalance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.tsyrkunou.jmpwep.application.model.GeneralAmount;
import com.tsyrkunou.jmpwep.application.model.customer.Customer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
public class Amount implements GeneralAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "amount_id_seq")
    @SequenceGenerator(name = "amount_id_seq", sequenceName = "amount_id_seq", allocationSize = 1)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(mappedBy = "amount")
    private Customer customer;

    private BigDecimal balance;

    @Override
    public void depositOnAmount(BigDecimal bigDecimal) {
        if(bigDecimal.compareTo(BigDecimal.ZERO) > 0)
            setBalance(this.balance.add(bigDecimal));
    }

    @Override
    public void deductFromAmount(BigDecimal bigDecimal) {
        if(bigDecimal.compareTo(BigDecimal.ZERO) > 0)
            setBalance(this.balance.subtract(bigDecimal));
    }
}
