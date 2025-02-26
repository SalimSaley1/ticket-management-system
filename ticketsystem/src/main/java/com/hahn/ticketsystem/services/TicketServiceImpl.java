package com.hahn.ticketsystem.services;

import com.hahn.ticketsystem.dtos.AuditLogResponseDTO;
import com.hahn.ticketsystem.dtos.TicketRequestDTO;
import com.hahn.ticketsystem.dtos.TicketResponseDTO;
import com.hahn.ticketsystem.dtos.UserCommentResponseDTO;
import com.hahn.ticketsystem.entities.AppUser;
import com.hahn.ticketsystem.entities.AuditLog;
import com.hahn.ticketsystem.entities.Ticket;
import com.hahn.ticketsystem.entities.UserComment;
import com.hahn.ticketsystem.enums.AuditType;
import com.hahn.ticketsystem.enums.Role;
import com.hahn.ticketsystem.enums.Status;
import com.hahn.ticketsystem.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final AppUserService appUserService;
    private final AuditLogService auditLogService;
    private final UserCommentService userCommentService;

    @Override
    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO, String email) throws AccessDeniedException {

        // Verify if user is authenticated
        /*AppUser currentUser = userService.getAuthenticatedUser();
        if (currentUser == null) {
            throw new IllegalStateException("Utilisateur non authentifié");
        }
         */

        //appUserService.initUsers();

        // Verify if user is EMPLOYEE
        AppUser appUser = this.appUserService.getAppUser(email);
        if (appUser == null) {
            throw new NoSuchElementException("User not found");
        }
        if (appUser.getRole() != Role.EMPLOYEE) {
            throw new AccessDeniedException("Only Employee can save ticket");
        }



        // Création du ticket
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketRequestDTO.getTitle());
        ticket.setDescription(ticketRequestDTO.getDescription());
        ticket.setPriority(ticketRequestDTO.getPriority());
        ticket.setCategory(ticketRequestDTO.getCategory());
        ticket.setStatus(Status.NEW);
        ticket.setCreationDate(LocalDateTime.now());
        ticket.setCreatedBy(appUser);

        Ticket savedTicket = ticketRepository.save(ticket);

        // Conversion en DTO
        return ticketToTicketResponseDTO(savedTicket);
    }

    public TicketResponseDTO ticketToTicketResponseDTO(Ticket ticket) {
        TicketResponseDTO ticketResponseDTO = mapObject(ticket, TicketResponseDTO.class);
        ticketResponseDTO.setCreatedByFullName(ticket.getCreatedBy().getFullName());
        List<UserCommentResponseDTO> userCommentResponseDTOList = new ArrayList<>();
        for(UserComment userComment :  ticket.getComments()){
            UserCommentResponseDTO userCommentResponseDTO = userCommentService.
                    userCommentToUserCommentResponseDTO(userComment);
            userCommentResponseDTOList.add(userCommentResponseDTO);
        }
        ticketResponseDTO.setComments(userCommentResponseDTOList);
        return ticketResponseDTO;
    }
    private static <T> T mapObject(Object source, Class<T> destinationClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(source, destinationClass);
    }


    @Override
    public TicketResponseDTO updateTicketStatus(Long id, Status newStatus, String email) throws AccessDeniedException {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket non trouvé"));

        // Vérifier que l'utilisateur est connecté et bien IT_SUPPORT
        /*if (itSupport.getRole() != Role.IT_SUPPORT) {
            throw new UnauthorizedOperationException("Only IT support can update ticket status");
        }

        AppUser performedBy = userRepository.findById(statusUpdateDTO.getPerformedById())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

         */



        // Verify if user is IT_SUPPORT
        AppUser appUser = this.appUserService.getAppUser(email);
        if (appUser == null) {
            throw new NoSuchElementException("User not found");
        }
        if (appUser.getRole() != Role.IT_SUPPORT) {
            throw new AccessDeniedException("Only IT support can update ticket status");
        }


        Status previousStatus = ticket.getStatus();

        if (previousStatus == newStatus) {
            throw new IllegalStateException("Le statut est déjà défini sur " + newStatus);
        }

        // Mise à jour du statut du ticket
        ticket.setStatus(newStatus);
        ticketRepository.save(ticket);

        // Création du log d'audit
        auditLogService.logChange(
                AuditType.TICKET_STATUS_CHANGE,
                previousStatus,
                newStatus,
                null,
                ticket,
                appUser
        );


        return ticketToTicketResponseDTO(ticket);
    }

    @Override
    public List<TicketResponseDTO> getAllTicketsOrUserTickets(String email){
        AppUser appUser = appUserService.getAppUser(email);
        if (appUser.getRole() == Role.EMPLOYEE){
            return this.getUserTickets(email);
        }
        return this.getAllTickets();
    }

    @Override
    public List<TicketResponseDTO> getUserTickets(String email) {

        AppUser appUser = this.appUserService.getAppUser(email);

        List<Ticket> ticketList = ticketRepository.findByCreatedByOrderByCreationDateDesc(appUser);

        return ticketList
                .stream()
                .map(ticket -> ticketToTicketResponseDTO(ticket))
                .collect(Collectors.toList());
    }



    // Obtenir tous les tickets (exemple d'utilisation pour IT Support)
    @Override
    public List<TicketResponseDTO> getAllTickets() {

        List<Ticket> ticketList = ticketRepository.findAll(
                Sort.by(Sort.Direction.DESC, "creationDate"));

        return ticketList
                .stream()
                .map(ticket -> ticketToTicketResponseDTO(ticket))
                .collect(Collectors.toList());
    }



    // Rechercher des tickets par ID et statut
    @Override
    public TicketResponseDTO searchTicket(Long ticketId, Status status) {
        Ticket ticket =  ticketRepository.findByIdAndStatus(ticketId, status);
        return ticketToTicketResponseDTO(ticket);
    }



    // Méthode pour consulter l'historique d'un ticket
    @Override
    public List<AuditLogResponseDTO> getTicketHistory(Long ticketId, String email) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        AppUser itSupport = appUserService.getAppUser(email);

        List<AuditLog> auditLogList = auditLogService.getAuditLogs(ticket, itSupport);

        return auditLogList
                .stream()
                .map(log -> auditLogService.auditLogToAuditLogResponseDTO(log))
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponseDTO getTicket(Long id){
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        return ticketToTicketResponseDTO(ticket);
    }

}
