package com.hahn.ticketsystem.entities;

import com.hahn.ticketsystem.enums.AuditType;
import com.hahn.ticketsystem.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuditLog {

    @Id
    @SequenceGenerator(
            name = "audit_log_seq",
            sequenceName = "AUDIT_LOG_SEQUENCE",
            allocationSize = 1 // increment
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_log_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditType auditType;

    @Column(name = "previous_status")
    private Status previousStatus;

    @Column(name = "new_status")
    private Status newStatus;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate ;



    @OneToOne
    //@JoinColumn(name = "comment_id")
    private UserComment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "performed_by_id", nullable = false)
    private AppUser performedBy;  // itSupport







}
