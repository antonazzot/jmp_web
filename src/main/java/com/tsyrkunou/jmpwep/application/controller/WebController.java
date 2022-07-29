package com.tsyrkunou.jmpwep.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/jmp/web")
public interface WebController {

        @SuppressWarnings("checkstyle:MethodParamPad")
        @Operation(description = "Get all user")
        @GetMapping
        String getAllCustomers(Model model);

        @Operation(description = "Get user by id")
        @GetMapping(value = "/getById/{id}")
        String getCustomersById(@PathVariable (value = "id") Long id, Model model);

        @Operation(description = "Get Boocked Tickets ")
        @GetMapping(value = "/bocked/{id}")
        String getBookedTickets(@PathVariable (value = "id") Long id,
                                 Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size);

        @Operation(description = "Get pdf ")
        @GetMapping(value = "/pdf/{id}")
        String getPdf(@PathVariable (value = "id") Long id, Model model);

        @Operation(description = "Marshaller ticket to xml")
        @GetMapping(value = "/xml/{id}")
        String getXml(@PathVariable (value = "id") Long id, Model model) throws JAXBException, IOException;

        @Operation(description = "Unmarshaller xml to ticket")
        @GetMapping(value = "/unxml/{id}")
        String getTicketFromXml(@PathVariable (value = "id") Long id, Model model) throws JAXBException, IOException;
}
