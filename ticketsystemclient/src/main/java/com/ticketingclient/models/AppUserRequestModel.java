package com.ticketingclient.models;

import com.ticketingclient.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequestModel {

    private String email;
    private String password;
    private String fullName;
    private Role role;


    public AppUserRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
