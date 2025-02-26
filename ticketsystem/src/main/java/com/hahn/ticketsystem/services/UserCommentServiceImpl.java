package com.hahn.ticketsystem.services;

import com.hahn.ticketsystem.dtos.UserCommentRequestDTO;
import com.hahn.ticketsystem.dtos.UserCommentResponseDTO;
import com.hahn.ticketsystem.entities.AppUser;
import com.hahn.ticketsystem.entities.Ticket;
import com.hahn.ticketsystem.entities.UserComment;
import com.hahn.ticketsystem.enums.AuditType;
import com.hahn.ticketsystem.enums.Role;
import com.hahn.ticketsystem.repositories.TicketRepository;
import com.hahn.ticketsystem.repositories.UserCommentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserCommentServiceImpl implements UserCommentService {

    private final UserCommentRepository userCommentRepository;

    private final TicketRepository ticketRepository;

    private final AppUserService appUserService;

    private final AuditLogService auditLogService;

    @Override
    public UserCommentResponseDTO addComment(UserCommentRequestDTO userCommentRequestDTO, Long ticketId, String email)  {

        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if (optionalTicket.isEmpty()) {
            throw new IllegalArgumentException("Ticket introuvable");
        }

        // Vérifier que l'utilisateur est bien IT_SUPPORT et CONNECTé
        /*
        if (itSupport.getRole() != Role.IT_SUPPORT) {
            throw new UnauthorizedOperationException("Only IT support can add comments");
        }

        AppUser currentUser = appUserService.getAuthenticatedUser();
        if (currentUser == null) {
            throw new IllegalStateException("Utilisateur non authentifié");
        }

         */


        // Verify if user is IT_SUPPORT
        AppUser appUser = this.appUserService.getAppUser(email);
        if (appUser == null) {
            //throw new NoSuchElementException("Utilisateur non identifié");
            return null;
        }
        if (appUser.getRole() != Role.IT_SUPPORT) {
            //throw new AccessDeniedException("Only IT support can add comments");
            return null;
        }

        Ticket ticket = optionalTicket.get();

        UserComment comment = new UserComment();
        comment.setContent(userCommentRequestDTO.getContent());
        comment.setCreationDate(LocalDateTime.now());
        comment.setTicket(ticket);
        comment.setUser(appUser);


        UserComment savedComment = userCommentRepository.save(comment);

        // Journalisation de l'ajout du commentaire
        auditLogService.logChange(
                AuditType.COMMENT_ADDED,
                ticket.getStatus(),
                ticket.getStatus(),
                comment,
                ticket,
                appUser

        );

        return userCommentToUserCommentResponseDTO(savedComment);
    }

    @Override
    public UserCommentResponseDTO userCommentToUserCommentResponseDTO(UserComment userComment) {
        UserCommentResponseDTO userCommentResponseDTO = mapObject(userComment, UserCommentResponseDTO.class);
        userCommentResponseDTO.setFullName(userComment.getUser().getFullName());
        userCommentResponseDTO.setTicketId(userComment.getTicket().getId());
        return userCommentResponseDTO;
    }
    private static <T> T mapObject(Object source, Class<T> destinationClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(source, destinationClass);
    }


}
