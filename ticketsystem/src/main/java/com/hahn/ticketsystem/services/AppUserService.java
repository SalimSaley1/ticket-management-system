package com.hahn.ticketsystem.services;

import com.hahn.ticketsystem.dtos.AppUserRequestDTO;
import com.hahn.ticketsystem.dtos.AppUserResponseDTO;
import com.hahn.ticketsystem.entities.AppUser;

import java.util.Optional;

public interface AppUserService {

    Optional<AppUserResponseDTO> getAppUserResponseDTO(String email);
    AppUser getAppUser(String email);

    void initUsers();

    AppUserResponseDTO save(AppUserRequestDTO appUserRequestDTO);
}
