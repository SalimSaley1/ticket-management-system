package com.hahn.ticketsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserComment {
    @Id
    @SequenceGenerator(
            name = "user_comment_seq",
            sequenceName = "USER_COMMENT_SEQUENCE",
            allocationSize = 1 // increment
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_comment_seq")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();





    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @OneToOne(mappedBy = "comment")
    private AuditLog auditLog;


}

