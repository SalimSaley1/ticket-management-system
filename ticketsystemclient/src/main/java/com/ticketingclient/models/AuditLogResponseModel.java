package com.ticketingclient.models;

import com.ticketingclient.enums.AuditType;
import com.ticketingclient.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogResponseModel {

    private Long id;
    private AuditType auditType;
    private Status previousStatus;
    private Status newStatus;
    private String comment;
    private LocalDateTime changeDate;
    private Long ticketId;
    private Long performedById;

}
