package com.tsyrkunou.jmpwep.application.metrics.config;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;
import com.tsyrkunou.jmpwep.application.repository.EventRepository;
import com.tsyrkunou.jmpwep.application.repository.OrderRepository;
import com.tsyrkunou.jmpwep.application.repository.TicketRepository;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PrometheusCustomMetricsCollector {

    private final MeterRegistry meterRegistry;

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    private final CustomerRepository customerRepository;

    @PostConstruct
    public void setup() {
        Gauge.builder("jmp.order.counter", this::orderCounter)
                .description("The number of order")
                .register(meterRegistry);
        Gauge.builder("jmp.event.counter", this::eventCounter)
                .description("The number of event")
                .register(meterRegistry);
        Gauge.builder("jmp.ticket.counter", this::ticketCounter)
                .description("The number of ticket")
                .register(meterRegistry);

        Counter.builder("jmp.Counter.Customer").description("Counter_Customer")
                .tag("user", "usr")
                .baseUnit(String.valueOf(customerRepository.count()))
                .register(meterRegistry);
    }

    private Number orderCounter() {
        return orderRepository.count();
    }

    private Number eventCounter() {
        return eventRepository.count();
    }

    private Number ticketCounter() {
        return ticketRepository.count();
    }

}
