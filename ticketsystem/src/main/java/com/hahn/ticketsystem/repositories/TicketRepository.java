package com.hahn.ticketsystem.repositories;

import com.hahn.ticketsystem.entities.AppUser;
import com.hahn.ticketsystem.entities.Ticket;
import com.hahn.ticketsystem.enums.Status;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    //Page<Ticket> findByCreatedBy(AppUser user , Pageable pageable);


    // Recherche par ID et status
    Ticket findByIdAndStatus(Long id, Status status);

    // Trouver les tickets d'un utilisateur spécifique (Employee)
    List<Ticket> findByCreatedByOrderByCreationDateDesc(AppUser appUser);

    //List<Ticket> findAll(Sort.by(Sort.Direction.DESC, "CreationDate"));

 /*

    // Recherche avancée combinant plusieurs critères
    @Query("SELECT t FROM Ticket t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:priority IS NULL OR t.priority = :priority) AND " +
            "(:category IS NULL OR t.category = :category)")
    List<Ticket> searchTickets(
            @Param("status") Status status,
            @Param("priority") Priority priority,
            @Param("category") Category category
    );


     */



}
