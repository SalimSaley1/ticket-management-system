package com.hahn.ticketsystem.dtos;

import com.hahn.ticketsystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponseDTO {

    private Long id;
    private String email;
    private String fullName;
    private Role role;
    private boolean exits;

}
