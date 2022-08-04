package com.tsyrkunou.jmpwep.application.model.ticket;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsyrkunou.jmpwep.application.model.ModelEntity;
import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.order.Oder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@XmlRootElement(name = "ticket")
@XmlType(propOrder = { "id", "placeNumber", "isFree", "coast", "event"})
@XmlAccessorType(XmlAccessType.FIELD)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity
public class Ticket implements ModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_seq")
    @SequenceGenerator(name = "ticket_id_seq", sequenceName = "ticket_id_seq", allocationSize = 1)
    @ToString.Include
    @EqualsAndHashCode.Include
    @XmlAttribute
    Long id;

    @Column(name = "place_number")
    @XmlElement(name = "place_number")
    Integer placeNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oder_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @XmlTransient
    Oder oder;

    @Column(name = "is_free")
    @XmlElement(name = "is_free")
    boolean isFree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @XmlTransient
    Event event;

    @XmlElement
    BigDecimal coast;
}
