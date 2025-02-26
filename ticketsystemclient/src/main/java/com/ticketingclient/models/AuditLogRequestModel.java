package com.ticketingclient.models;


import com.ticketingclient.enums.AuditType;
import com.ticketingclient.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogRequestModel {

    private AuditType auditType;
    private Status previousStatus;
    private Status newStatus;
    private Long commentId;
    private Long ticketId;
    private Long performedById;
}
