package com.hahn.ticketsystem.services;

import com.hahn.ticketsystem.dtos.AuditLogResponseDTO;
import com.hahn.ticketsystem.dtos.UserCommentResponseDTO;
import com.hahn.ticketsystem.entities.AppUser;
import com.hahn.ticketsystem.entities.AuditLog;
import com.hahn.ticketsystem.entities.Ticket;
import com.hahn.ticketsystem.entities.UserComment;
import com.hahn.ticketsystem.enums.AuditType;
import com.hahn.ticketsystem.enums.Status;
import com.hahn.ticketsystem.repositories.AuditLogRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuditLogServiceImpl implements AuditLogService{

    private final AuditLogRepository auditLogRepository;


    @Override
    public AuditLog logChange(
            AuditType auditType,
            Status previousStatus,
            Status newStatus,
            UserComment comment,
            Ticket ticket,
            AppUser itSupport
    ) {

        AuditLog auditLog = new AuditLog();

        auditLog.setAuditType(auditType);
        auditLog.setPreviousStatus(previousStatus);
        auditLog.setNewStatus(newStatus);
        auditLog.setComment(comment);
        auditLog.setChangeDate(LocalDateTime.now());
        auditLog.setTicket(ticket);
        auditLog.setPerformedBy(itSupport);

        return auditLogRepository.save(auditLog);
    }

    @Override
    public List<AuditLog> getAuditLogs(Ticket ticket, AppUser itSupport) {
        return auditLogRepository.findAllByTicketAndPerformedByOrderByChangeDateDesc(ticket, itSupport);
    }

    @Override
    public AuditLogResponseDTO auditLogToAuditLogResponseDTO(AuditLog auditLog) {
        AuditLogResponseDTO auditLogResponseDTO = mapObject(auditLog, AuditLogResponseDTO.class);
        auditLogResponseDTO.setTicketId(auditLog.getTicket().getId());
        return auditLogResponseDTO;
    }
    private static <T> T mapObject(Object source, Class<T> destinationClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(source, destinationClass);
    }
}
