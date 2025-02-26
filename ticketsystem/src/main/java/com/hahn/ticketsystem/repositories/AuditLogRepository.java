package com.hahn.ticketsystem.repositories;

import com.hahn.ticketsystem.entities.AppUser;
import com.hahn.ticketsystem.entities.AuditLog;
import com.hahn.ticketsystem.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    /*
    // Trouver tous les logs d'un ticket spécifique
    List<AuditLog> findByTicketOrderByChangeDateDesc(Ticket ticket);

    // Trouver les logs par type d'audit
    List<AuditLog> findByAuditTypeOrderByChangeDateDesc(AuditType auditType);

    // Trouver les logs par utilisateur qui a effectué l'action
    List<AuditLog> findByPerformedByOrderByChangeDateDesc(AppUser user);

    // Requête personnalisée pour obtenir l'historique complet d'un ticket
    @Query("SELECT a FROM AuditLog a WHERE a.ticket.id = :ticketId ORDER BY a.changeDate DESC")
    List<AuditLog> findTicketHistory(@Param("ticketId") Long ticketId);

    // Trouver les derniers changements de statut d'un ticket
    @Query("SELECT a FROM AuditLog a WHERE a.ticket.id = :ticketId AND a.auditType = 'TICKET_STATUS_CHANGE' ORDER BY a.changeDate DESC")
    List<AuditLog> findStatusChanges(@Param("ticketId") Long ticketId);


     */



    List<AuditLog> findAllByTicketAndPerformedByOrderByChangeDateDesc(
            Ticket ticket,
            AppUser itSupport);


}
