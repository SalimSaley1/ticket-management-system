package com.hahn.ticketsystem.dtos;

import com.hahn.ticketsystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequestDTO {

    private String email;
    private String password;
    private String fullName;
    private Role role;

}
