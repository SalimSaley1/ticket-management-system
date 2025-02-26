package com.hahn.ticketsystem.configuration;

import com.hahn.ticketsystem.dtos.AppUserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private AppUserResponseDTO user;

    // Getters, setters...
}
