package com.tsyrkunou.jmpwep.application.steps;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import com.tsyrkunou.jmpwep.application.model.event.CreateEventRequest;
import com.tsyrkunou.jmpwep.application.model.event.EventResponse;
import com.tsyrkunou.jmpwep.application.model.order.CreateOrderRequest;
import com.tsyrkunou.jmpwep.application.model.order.OrderResponse;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingSteps {

    public static MockMvcResponse createRandomEvent() {
        CreateEventRequest createEventRequest = CreateEventRequest.builder()
                .name(RandomStringUtils.randomAlphanumeric(100))
                .amountOfPlace(50)
                .coastOfTicket(BigDecimal.TEN)
                .balance(BigDecimal.valueOf(1000))
                .build();

        return given().contentType(ContentType.JSON)
                .body(createEventRequest)
                .when()
                .post(EndPoints.Event.CREATE)
                .thenReturn();
    }

    public static MockMvcResponse createEvent(CreateEventRequest createEventRequest) {

        return given().contentType(ContentType.JSON)
                .body(createEventRequest)
                .when()
                .post(EndPoints.Event.CREATE)
                .thenReturn();
    }

    public static EventResponse createEventResponse(CreateEventRequest request) {
        return createEvent(request)
                .then()
                .expect(status().isOk())
                .extract().as(EventResponse.class);
    }

    public static EventResponse createRandomEventResponse() {
        return createRandomEvent()
                .then()
                .expect(status().isCreated())
                .extract().as(EventResponse.class);
    }

    public static MockMvcResponse createOrder(CreateOrderRequest request) {
        return given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(EndPoints.Order.CREATE)
                .thenReturn();
    }

    public static OrderResponse createOrderResponse(CreateOrderRequest request) {
        return createOrder(request)
                .then()
                .expect(status().isOk())
                .extract().as(OrderResponse.class);
    }
}
