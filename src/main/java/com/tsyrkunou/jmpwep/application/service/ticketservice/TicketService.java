package com.tsyrkunou.jmpwep.application.service.ticketservice;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;

import javax.xml.bind.JAXBException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.event.Event;
import com.tsyrkunou.jmpwep.application.model.ticket.Ticket;
import com.tsyrkunou.jmpwep.application.repository.TicketRepository;
import com.tsyrkunou.jmpwep.application.service.commonservice.MarshallerService;
import com.tsyrkunou.jmpwep.application.utils.NotFoundException;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketSearch ticketSearch;
    private final TicketRepository ticketRepository;
    private final MarshallerService marshallerService;

    public List<Ticket> getFreeTicket(Event event) {
        return ticketSearch.findFreeTicketWithEvent(event);
    }

    public Page<Ticket> getBockedTickets(Long id, PageRequest pageable) {
        List<Ticket> list = ticketSearch.findBockedTicketByUser(id);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), list.size());
    }

    public List<Ticket> getBockedTickets(Long id) {
        return ticketSearch.findBockedTicketByUser(id);
    }

    public Ticket findOne(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket with id: " + ticketId + " not found"));
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Transactional
    public void unMarshalledAndSave(Long ticketId) throws JAXBException, IOException {
        Ticket unmarshall = marshallerService.unmarshall(ticketId);
        ticketRepository.save(unmarshall);
    }

    public List<Ticket> getTicketByNumberOfPlace(List<Integer> numberOfPlace) {
        return ticketSearch.findFreeTicketByNumberOfPlace(numberOfPlace);
    }
}

