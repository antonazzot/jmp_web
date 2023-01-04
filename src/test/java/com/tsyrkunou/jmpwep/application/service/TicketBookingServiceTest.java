//package com.tsyrkunou.jmpwep.application.service;
//
//import static com.tsyrkunou.jmpwep.application.steps.BookingSteps.createEventResponse;
//
//import java.math.BigDecimal;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//
//import com.tsyrkunou.jmpwep.application.AbstractTest;
//import com.tsyrkunou.jmpwep.application.model.event.CreateEventRequest;
//import com.tsyrkunou.jmpwep.application.model.event.EventResponse;
//import com.tsyrkunou.jmpwep.application.model.order.CreateOrderRequest;
//import com.tsyrkunou.jmpwep.application.security.web.model.RegisterRequest;
//
//@AutoConfigureMockMvc
//class TicketBookingServiceTest extends AbstractTest {
//
//    private final String eventName = "TestEventName";
//    private final Integer eventPlaces = 20;
//    private final Integer amountOfPlacesForBocking = 4;
//    private final BigDecimal customerBalance = BigDecimal.valueOf(1001);
//    private final BigDecimal eventBalance = BigDecimal.valueOf(10001);
//    private final BigDecimal coastOfTicket = BigDecimal.valueOf(100);
//
//    @BeforeEach
//    private void init() {
//        eventRepository.deleteAll();
//        orderRepository.deleteAll();
//        customerRepository.deleteAll();
//        ticketRepository.deleteAll();
//        userTokenRepository.deleteAll();
//    }
//
//    @Test
//    void createOrder() {
//        String token = generateAdminToken();
//        CreateEventRequest request = CreateEventRequest.builder()
//                .name(eventName)
//                .amountOfPlace(eventPlaces)
//                .coastOfTicket(coastOfTicket)
//                .balance(eventBalance)
//                .build();
//
//        EventResponse event = createEventResponse(request, token);
//
//        String userName = "TestUserName";
//
//        CreateOrderRequest orderRequest = CreateOrderRequest.builder()
//                .eventName(eventName)
//                .amountOfPlace(amountOfPlacesForBocking)
//                .customerName(userName)
//                .customerBalance(customerBalance)
//                .newCustomer(true)
//                .build();
//
////        OrderResponse order = createOrderResponse(orderRequest, token);
////
////        assertThat(order.getTickets()).hasSize(amountOfPlacesForBocking);
////        assertThat(customerService.findOne(userName).getAmount().getBalance())
////                .isEqualTo(
////                        customerBalance.subtract(coastOfTicket.multiply(BigDecimal.valueOf(amountOfPlacesForBocking))));
////        assertThat(ticketService.getFreeTicket(eventService.findOne(event.getId()))).hasSize(
////                eventPlaces - amountOfPlacesForBocking);
////
////        String notExistUserName = "not_exist_user_name";
////        assertThatThrownBy(() -> customerService.findOne(notExistUserName))
////                .isInstanceOf(NotFoundException.class)
////                .hasMessage("Customer with name: " + notExistUserName + " not found");
////
////        long notExistUserId = 9999L;
////        assertThatThrownBy(() -> customerService.findOne(notExistUserId))
////                .isInstanceOf(NotFoundException.class)
////                .hasMessage("Customer with id: " + notExistUserId + " not found");
////
////        CreateOrderRequest orderRequest2 = CreateOrderRequest.builder()
////                .eventName(eventName)
////                .amountOfPlace(amountOfPlacesForBocking)
////                .customerName("insufficient_balance_user")
////                .customerBalance(BigDecimal.TEN)
////                .newCustomer(true)
////                .build();
////
////        MockMvcResponse order1 = BookingSteps.createOrder(orderRequest2, token);
////
////        assertThat(order1.thenReturn().mvcResult().getResolvedException())
////                .isInstanceOf(MyAppException.class)
////                .hasMessage("insufficient funds");
////
////        CreateOrderRequest orderRequest3 = CreateOrderRequest.builder()
////                .eventName(eventName)
////                .amountOfPlace(eventPlaces)
////                .customerName("not_free_place_user")
////                .customerBalance(BigDecimal.TEN)
////                .newCustomer(true)
////                .build();
////
////        MockMvcResponse order3 = BookingSteps.createOrder(orderRequest3, token);
////
////        assertThat(order3.thenReturn().mvcResult().getResolvedException())
////                .isInstanceOf(MyAppException.class)
////                .hasMessage("No more free space");
//
//    }
//
//    private String generateAdminToken() {
//        return authenticationService.registerAdmin(RegisterRequest.builder()
//                .firstname("admin")
//                .lastname("admin")
//                .email("admin.email@email.email")
//                .password("verysecretpassword")
//                .build()).getToken();
//    }
//}