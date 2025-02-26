package com.hahn.ticketsystem.services;

import com.hahn.ticketsystem.dtos.AuditLogResponseDTO;
import com.hahn.ticketsystem.dtos.TicketRequestDTO;
import com.hahn.ticketsystem.dtos.TicketResponseDTO;
import com.hahn.ticketsystem.entities.AppUser;
import com.hahn.ticketsystem.entities.AuditLog;
import com.hahn.ticketsystem.entities.Ticket;
import com.hahn.ticketsystem.entities.UserComment;
import com.hahn.ticketsystem.enums.AuditType;
import com.hahn.ticketsystem.enums.Status;

import java.util.List;
import java.util.Optional;

public interface AuditLogService {
    AuditLog logChange(
            AuditType auditType,
            Status previousStatus,
            Status newStatus,
            UserComment comment,
            Ticket ticket,
            AppUser itSupport
    );

    List<AuditLog> getAuditLogs(Ticket ticket, AppUser itSupport);

    AuditLogResponseDTO auditLogToAuditLogResponseDTO(AuditLog auditLog);


}
