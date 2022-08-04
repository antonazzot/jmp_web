package com.tsyrkunou.jmpwep.application.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.tsyrkunou.jmpwep.application.AbstractTest;
import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.service.customerservice.CustomerService;
import com.tsyrkunou.jmpwep.application.service.ticketservice.TicketService;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTests extends AbstractTest {

    @MockBean
    private CustomerService customerService;
    @MockBean
    private TicketService ticketService;

    private final String root = "/jmp/web";

    @Test
    @DisplayName("Case with get all customer")
    void getAllCustomersTest() throws Exception {
        when(customerService.findAll()).thenReturn(List.of(Customer.builder().name("TestName").build()));

        this.mockMvc.perform(get(root))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestName")));
    }

    @Test
    @DisplayName("Case with get all customer")
    void returnPdf() throws Exception {
        when(ticketService.getBockedTickets(1L)).thenReturn(List.of(Ticket.builder()
                .placeNumber(3)
                .coast(BigDecimal.TEN)
                .id(1L)
                .build()));

        mockMvc
                .perform(get(root + "/getpdf/1").accept(MediaType.APPLICATION_PDF_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }
}