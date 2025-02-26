package com.hahn.ticketsystem.entities;

import com.hahn.ticketsystem.enums.Category;
import com.hahn.ticketsystem.enums.Priority;
import com.hahn.ticketsystem.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Ticket {

    @Id
    @SequenceGenerator(
            name = "ticket_seq",
            sequenceName = "TICKET_SEQUENCE",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime creationDate;




    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "created_by_id", nullable = false)
    private AppUser createdBy;

    @OneToMany(mappedBy = "ticket",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "ticket",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AuditLog> auditLogs = new ArrayList<>();



}
