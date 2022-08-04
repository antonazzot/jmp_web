package com.tsyrkunou.jmpwep.application.model.amounts.eventbalance;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tsyrkunou.jmpwep.application.model.amounts.GeneralAmount;
import com.tsyrkunou.jmpwep.application.model.event.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(name = "event_amount")
@Entity
public class EventAmount implements GeneralAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_amount_id_seq")
    @SequenceGenerator(name = "event_amount_id_seq", sequenceName = "event_amount_id_seq", allocationSize = 1)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "eventAmount")
    private Event event;

    private BigDecimal balance;

    @Override
    public void depositOnAmount(BigDecimal bigDecimal) {
        if (bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
            setBalance(this.balance.add(bigDecimal));
        }
    }

    @Override
    public void deductFromAmount(BigDecimal bigDecimal) {
        if (bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
            setBalance(this.balance.subtract(bigDecimal));
        }
    }

    @Override
    public Map<String, String> watchBalance() {
        String ownerName = getEvent().getName();
        BigDecimal bigDecimal = getBalance();
        String ownerBalance = bigDecimal.toString();
        return Map.of("ownerName", ownerName, "ownerBalance", ownerBalance);
    }
}