package com.hahn.ticketsystem.services;

import com.hahn.ticketsystem.dtos.AuditLogResponseDTO;
import com.hahn.ticketsystem.dtos.TicketRequestDTO;
import com.hahn.ticketsystem.dtos.TicketResponseDTO;
import com.hahn.ticketsystem.enums.Status;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface TicketService {
    //TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO, AppUser itSupport);
    TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO, String email) throws AccessDeniedException;
    TicketResponseDTO updateTicketStatus(Long id, Status newStatus, String email)throws AccessDeniedException;

    TicketResponseDTO getTicket(Long id);

    List<TicketResponseDTO> getAllTicketsOrUserTickets(String email);

    List<TicketResponseDTO> getUserTickets(String email);

    List<TicketResponseDTO> getAllTickets();

    List<AuditLogResponseDTO> getTicketHistory(Long ticketId, String email);

    TicketResponseDTO searchTicket(Long ticketId, Status status);



}
