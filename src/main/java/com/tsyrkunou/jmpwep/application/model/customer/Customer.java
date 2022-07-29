package com.tsyrkunou.jmpwep.application.model.customer;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tsyrkunou.jmpwep.application.model.ModelEntity;
import com.tsyrkunou.jmpwep.application.model.order.Oder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Customer implements ModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
    @ToString.Include
    @EqualsAndHashCode.Include
    Long id;

    String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    Set<Oder> oders;

    BigDecimal balance;

    public void addOder(Oder oder) {
        if (oders == null) {
            oders = new HashSet<>();
        }
        oders.add(oder);
        oder.setCustomer(this);
    }

    public void removeOrder(Oder oder) {
        oders.remove(oder);
        oder.setCustomer(null);
    }
}
