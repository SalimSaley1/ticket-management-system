package com.ticketingclient.models;

import com.ticketingclient.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponseModel {

    private Long id;
    private String email;
    private String fullName;
    private Role role;
    private boolean exits;

}
