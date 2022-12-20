package com.kainv.http.service;


import com.kainv.http.dao.TicketDao;
import com.kainv.http.dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class TicketService {
    private static final TicketService INSTANCE = new TicketService();

    private TicketService() {
    }

    public static TicketService getInstance() {
        return INSTANCE;
    }

    private final TicketDao ticketDao = TicketDao.getInstance();

    /**
     * <h2>Вынимаем то, что нам нужно при помощи TicketDto</h2>
     *
     * @param flightId
     * @return
     */
    public List<TicketDto> findAllByFlightId(Long flightId) {
        return ticketDao.findByFlightId(flightId).stream()
                .map(ticket -> new TicketDto(
                        ticket.getId(),
                        ticket.getFlight_id(),
                        ticket.getSeatNo()
                ))
                .collect(toList());
    }
}
