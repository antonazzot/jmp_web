//package com.tsyrkunou.jmpwep.application.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import java.math.BigDecimal;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import com.tsyrkunou.jmpwep.application.AbstractTest;
//import com.tsyrkunou.jmpwep.application.model.event.Event;
//import com.tsyrkunou.jmpwep.application.model.event.EventData;
//import com.tsyrkunou.jmpwep.application.model.order.CreateOrderData;
//import com.tsyrkunou.jmpwep.application.model.order.Oder;
//import com.tsyrkunou.jmpwep.application.utils.NotFoundException;
//import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.MyAppException;
//
//class TicketBookingServiceTest extends AbstractTest {
//
//    private final String eventName = "TestEventName";
//    private final Integer eventPlaces = 20;
//    private final Integer amountOfPlacesForBocking = 4;
//    private final BigDecimal customerBalance = BigDecimal.valueOf(1001);
//    private final BigDecimal coastOfTicket = BigDecimal.valueOf(100);
//
//    @BeforeEach
//    private void init() {
//        eventRepository.deleteAll();
//        orderRepository.deleteAll();
//        customerRepository.deleteAll();
//        ticketRepository.deleteAll();
//    }
//
//    @SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:LocalVariableName"})
//    @Test
//    void createOrder() {
//        Event event = eventService.createEvent(new EventData(eventName, eventPlaces, coastOfTicket));
//
//        String userName = "TestUserName";
//        Oder testOrder = ticketBookingService.createOrder(CreateOrderData.builder()
//                .eventName(eventName)
//                .customerName(userName)
//                .customerBalance(customerBalance)
//                .newCustomer(true)
//                .amountOfPlace(amountOfPlacesForBocking)
//                .build());
//
//        assertThat(testOrder.getTickets()).hasSize(amountOfPlacesForBocking);
//        assertThat(customerService.findOne(userName).getBalance())
//                .isEqualTo(
//                        customerBalance.subtract(coastOfTicket.multiply(BigDecimal.valueOf(amountOfPlacesForBocking))));
//        assertThat(ticketService.getFreeTicket(event)).hasSize(eventPlaces - amountOfPlacesForBocking);
//
//        String not_exist_user_name = "not_exist_user_name";
//        assertThatThrownBy(() -> customerService.findOne(not_exist_user_name))
//                .isInstanceOf(NotFoundException.class)
//                .hasMessage("Customer with name: " + not_exist_user_name + " not found");
//
//        long not_exist_user_id = 9999L;
//        assertThatThrownBy(() -> customerService.findOne(not_exist_user_id))
//                .isInstanceOf(NotFoundException.class)
//                .hasMessage("Customer with id: " + not_exist_user_id + " not found");
//
//        assertThatThrownBy(() -> ticketBookingService.createOrder(CreateOrderData.builder()
//                .eventName(eventName)
//                .customerName("insufficient_balance_user")
//                .customerBalance(BigDecimal.TEN)
//                .newCustomer(true)
//                .amountOfPlace(amountOfPlacesForBocking)
//                .build())).isInstanceOf(MyAppException.class)
//                .hasMessage("insufficient funds");
//
//        assertThatThrownBy(() -> ticketBookingService.createOrder(CreateOrderData.builder()
//                .eventName(eventName)
//                .customerName("not_free_place_user")
//                .newCustomer(true)
//                .amountOfPlace(eventPlaces)
//                .build())).isInstanceOf(MyAppException.class)
//                .hasMessage("No more free space");
//    }
//}