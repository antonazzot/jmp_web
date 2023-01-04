package com.tsyrkunou.jmpwep.application.model.customer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tsyrkunou.jmpwep.application.model.ModelEntity;
import com.tsyrkunou.jmpwep.application.model.amounts.customerbalance.Amount;
import com.tsyrkunou.jmpwep.application.model.order.Oder;
import com.tsyrkunou.jmpwep.application.security.model.Authority;
import com.tsyrkunou.jmpwep.application.security.model.User;
import com.tsyrkunou.jmpwep.application.security.model.UserToken;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@TypeDef(name = "psql_enum", typeClass = PostgreSQLEnumType.class)
@Entity
public class Customer extends User implements ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
    @ToString.Include
    @EqualsAndHashCode.Include
    public Long id;

    String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    Set<Oder> oders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "amount_id", referencedColumnName = "id")
    Amount amount;

    @Type(type = "psql_enum")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private Collection<Authority> authorities;

    @OneToOne
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    private UserToken userToken;

    private String email;

    @Override
    public String getToken() {
        return this.userToken.getToken();
    }

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
