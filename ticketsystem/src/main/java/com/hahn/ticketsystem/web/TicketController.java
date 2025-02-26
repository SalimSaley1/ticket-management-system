package com.hahn.ticketsystem.web;


import com.hahn.ticketsystem.dtos.*;
import com.hahn.ticketsystem.enums.Status;
import com.hahn.ticketsystem.services.TicketService;
import com.hahn.ticketsystem.services.UserCommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController {

    private final TicketService ticketService;
    private final UserCommentService userCommentService;

    private JwtDecoder jwtDecoder;

    @PostMapping
    public TicketResponseDTO createTicket(@RequestBody TicketRequestDTO ticketRequestDTO,
                                          @RequestHeader("Authorization") String jwtToken) {

        String token = jwtToken.replace("Bearer ", "");
        Jwt jwt = jwtDecoder.decode(token);
        String email = jwt.getSubject();

        try {

            return  ticketService.createTicket(ticketRequestDTO, email);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @PutMapping(value = "/{id}")
    public TicketResponseDTO updateTicket(@PathVariable Long id,
                                          @RequestBody Status newStatus,
                                          @RequestHeader("Authorization") String jwtToken) {

            String token = jwtToken.replace("Bearer ", "");
            Jwt jwt = jwtDecoder.decode(token);
            String email = jwt.getSubject();

            try {

            return  ticketService.updateTicketStatus(id, newStatus, email);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @GetMapping("/{id}")
    public TicketResponseDTO getTicket(@PathVariable Long id) {

        try {

            return ticketService.getTicket(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @GetMapping
    public List<TicketResponseDTO> getAllTicketsOrUserTickets(
            @RequestHeader("Authorization") String jwtToken) {

        String token = jwtToken.replace("Bearer ", "");
        Jwt jwt = jwtDecoder.decode(token);
        String email = jwt.getSubject();

        try {

            return ticketService.getAllTicketsOrUserTickets(email);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @GetMapping(value = "/{id}/history")
    public List<AuditLogResponseDTO> getTicketHistory(@PathVariable(name = "id") Long ticketId,
                                                      @RequestHeader("Authorization") String jwtToken) {

        String token = jwtToken.replace("Bearer ", "");
        Jwt jwt = jwtDecoder.decode(token);
        String email = jwt.getSubject();

        try {

            return ticketService.getTicketHistory(ticketId, email);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }



    @GetMapping(value = "/{id}/search")
    public TicketResponseDTO searchTicket(@PathVariable(name = "id") Long ticketId, @RequestParam Status status) {

        try {

            return ticketService.searchTicket(ticketId, status);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @PostMapping(value = "/{id}/comment")
    public UserCommentResponseDTO addComment(@PathVariable(name = "id") Long ticketId,
                                             @RequestBody UserCommentRequestDTO userCommentRequestDTO,
                                             @RequestHeader("Authorization") String jwtToken) {

        String token = jwtToken.replace("Bearer ", "");
        Jwt jwt = jwtDecoder.decode(token);
        String email = jwt.getSubject();

        try {

            return  userCommentService.addComment(userCommentRequestDTO, ticketId, email);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }







}
