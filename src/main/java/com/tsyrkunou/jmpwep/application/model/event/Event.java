package com.tsyrkunou.jmpwep.application.model.event;

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

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tsyrkunou.jmpwep.application.model.ModelEntity;
import com.tsyrkunou.jmpwep.application.model.amounts.eventbalance.EventAmount;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "tickets",
                attributeNodes = {
                        @NamedAttributeNode(value = "ticket")
                }
        )
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "event")
@XmlType(propOrder = {"id", "name"})
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity
public class Event implements ModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_seq")
    @SequenceGenerator(name = "event_id_seq", sequenceName = "event_id_seq", allocationSize = 1)
    @ToString.Include
    @EqualsAndHashCode.Include
    @XmlAttribute
    Long id;

    @XmlElement
    String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_amount_id", referencedColumnName = "id")
    EventAmount eventAmount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @XmlTransient
    Set<Ticket> ticket;

    public void addTicket(Ticket ticket1) {
        if (ticket == null) {
            ticket = new HashSet<>();
        }
        ticket.add(ticket1);
        ticket1.setEvent(this);
    }

    public void removeTicket(Ticket ticket1) {
        ticket.remove(ticket1);
        ticket1.setEvent(null);
    }
}
