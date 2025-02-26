package com.hahn.ticketsystem.dtos;

import com.hahn.ticketsystem.enums.AuditType;
import com.hahn.ticketsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogRequestDTO {

    private AuditType auditType;
    private Status previousStatus;
    private Status newStatus;
    private Long commentId;
    private Long ticketId;
    private Long performedById;
}
