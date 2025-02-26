package com.hahn.ticketsystem.entities;

import com.hahn.ticketsystem.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @SequenceGenerator(
            name = "app_user_seq",
            sequenceName = "APP_USER_SEQUENCE",
            allocationSize = 1 // increment
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;




    @OneToMany(mappedBy = "createdBy")
    private List<Ticket> createdTickets = new ArrayList<>();

    @OneToMany(mappedBy = "performedBy")
    private List<AuditLog> performedActions = new ArrayList<>();



}