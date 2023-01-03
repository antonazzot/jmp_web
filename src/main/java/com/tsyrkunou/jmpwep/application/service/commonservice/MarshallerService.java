package com.tsyrkunou.jmpwep.application.service.commonservice;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Service;

import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.MyAppException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarshallerService {

    public void marshal(List<Ticket> list) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Ticket.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        list.forEach(ticket -> {
            try {
                String ticketPathName = "src/main/resources/xmlparse/ticket" + ticket.getId() + ".xml";
                mar.marshal(ticket, new File(ticketPathName));
            } catch (JAXBException e) {
                log.error(e.getMessage());
                throw new MyAppException(e.getMessage());
            }
        });

    }

    public Ticket unmarshall(Long ticketId) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Ticket.class);
        String ticketPathName = "src/main/resources/xmlparse/ticket" + ticketId + ".xml";
        return (Ticket) context.createUnmarshaller()
                .unmarshal(new FileReader(ticketPathName));
    }
}
