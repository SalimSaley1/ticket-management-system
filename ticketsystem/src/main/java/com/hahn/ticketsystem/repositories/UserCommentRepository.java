package com.hahn.ticketsystem.repositories;

import com.hahn.ticketsystem.entities.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
/*
    List<UserComment> findByTicketOrderByCreationDateDesc(Ticket ticket);

    List<UserComment> findByUserOrderByCreationDateDesc(AppUser user);

    @Query("SELECT c FROM UserComment c WHERE c.ticket.id = :ticketId ORDER BY c.creationDate DESC")
    List<UserComment> findTicketComments(@Param("ticketId") Long ticketId);


*/

}
