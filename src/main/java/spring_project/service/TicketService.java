package spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_project.dto.TicketRequests;
import spring_project.entity.Ticket;
import spring_project.entity.TicketStatus;
import spring_project.entity.TripCar;
import spring_project.entity.User;
import spring_project.mapper.TicketMapper;
import spring_project.repository.TickerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TickerRepository ticketRepository;

    @Autowired
    private TripCarService tripCarService;

    @Autowired
    private ManageUserByAdminService manageUserByAdminService;

    @Autowired
    private TicketMapper ticketMapper;

    public List<TicketRequests> getAllTicketsByUserId(Long id) {
        List<Ticket> listTickets = ticketRepository.findByUserId(id);
        return listTickets.stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    public TicketRequests getTicketById(Long tickerId) {
        Ticket ticket = ticketRepository.findById(tickerId).orElse(null);
        return ticketMapper.toDto(ticket);
    }

    public List<TicketRequests> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    public Ticket createTicket(TicketRequests ticketRequests) {
        Ticket newTicket = new Ticket();
        newTicket.setSeatNumber(ticketRequests.getSeatNumber());
        newTicket.setStatus(TicketStatus.PENDING);
        User user = manageUserByAdminService.findById(ticketRequests.getId());
        TripCar tripCar = tripCarService.findTripCarById(ticketRequests.getTripCarId());

        if (tripCar.getEmptySeatNumber() <= 0) {
            throw new RuntimeException("Chuyến xe đã hết  ghế trống");
        }

        tripCar.setEmptySeatNumber(tripCar.getEmptySeatNumber() - 1);
        tripCarService.save(tripCar);
        newTicket.setUser(user);
        newTicket.setTripCar(tripCar);
        return ticketRepository.save(newTicket);
    }

    public Ticket updateTicketStatus(Long ticketId, TicketStatus status) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setStatus(status);
            return ticketRepository.save(ticket);
        }
        return null;
    }

    public Ticket deleteTicketById(Long ticketId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isPresent()) {
            ticketRepository.deleteById(ticketId);
            return ticket.get();
        }
        return null;
    }

}
