package com.hahn.ticketsystem.web;

import com.hahn.ticketsystem.dtos.AppUserRequestDTO;
import com.hahn.ticketsystem.dtos.AppUserResponseDTO;
import com.hahn.ticketsystem.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin("*")
public class AppUserController {

    private  final AppUserService appUserService;

    @PostMapping
    public AppUserResponseDTO registerNewUser(@RequestBody AppUserRequestDTO appUserRequestDTO){

        //appUserRequestDTO.setId(null);
        AppUserResponseDTO savedUser = appUserService.save(appUserRequestDTO);

        return savedUser;
    }

}
