package com.ticketingclient.services;

import com.ticketingclient.models.AppUserResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private AppUserResponseModel user;

    // Getters, setters...
}
