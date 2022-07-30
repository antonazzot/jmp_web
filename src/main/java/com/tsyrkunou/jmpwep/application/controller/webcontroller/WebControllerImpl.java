package com.tsyrkunou.jmpwep.application.controller.webcontroller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.JAXBException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.service.customerservice.CustomerService;
import com.tsyrkunou.jmpwep.application.service.commonservice.MarshallerService;
import com.tsyrkunou.jmpwep.application.service.commonservice.PdfGenerateService;
import com.tsyrkunou.jmpwep.application.service.ticketservice.TicketService;

@Controller
@RequiredArgsConstructor
public class WebControllerImpl implements WebController {
    private final CustomerService customerService;
    private final TicketService ticketService;
    private final PdfGenerateService pdfGenerateServiceImp;
    private final MarshallerService marshallerService;

    @Override
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customer/all";
    }

    @Override
    public String getCustomersById(Long id, Model model) {
        Customer one = customerService.findOne(id);
        model.addAttribute("customer", one);
        return "customer/one";
    }

    @Override
    public String getBookedTickets(Long id, Model model, Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Ticket> ticketPage = ticketService.getBockedTickets(id, PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("ticketPage", ticketPage);

        int totalPages = ticketPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "tickets/listTickets.html";
    }

    public String getBookedTickets(Long id, Model model) {

        List<Ticket> ticketList = ticketService.getBockedTickets(id);

        model.addAttribute("ticketList", ticketList);

        return "tickets/ticket.html";
    }

    @Override
    public String getPdf(Long id, Model model) {
        String bookedTickets = getBookedTickets(id, model);
        List<Ticket> ticketList = ticketService.getBockedTickets(id);
        Map<String, Object> context = Map.of("ticketList", ticketList);
        pdfGenerateServiceImp.generatePdfFile("tickets/ticket", context, "Mypdf");
        return bookedTickets;
    }

    @Operation(description = "Marshaller ticket to xml")
    @Override
    public String getXml(Long id, Model model) throws JAXBException, IOException {
        List<Ticket> ticketList = ticketService.getBockedTickets(id);
        marshallerService.marshal(ticketList);
        return "pdfgen/itwasenerated.html";
    }

    @Override
    public String getTicketFromXml(Long id, Model model) throws JAXBException, IOException {
        marshallerService.unmarshall(id);
        return "pdfgen/itwasenerated.html";
    }

}
