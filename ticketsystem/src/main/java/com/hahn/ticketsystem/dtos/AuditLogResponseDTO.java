package com.hahn.ticketsystem.dtos;

import com.hahn.ticketsystem.enums.AuditType;
import com.hahn.ticketsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogResponseDTO {

    private Long id;
    private AuditType auditType;
    private Status previousStatus;
    private Status newStatus;
    private String comment;
    private LocalDateTime changeDate;
    private Long ticketId;
    private Long performedById;

}
