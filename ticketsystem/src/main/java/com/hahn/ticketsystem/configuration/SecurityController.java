package com.hahn.ticketsystem.configuration;


import com.hahn.ticketsystem.dtos.AppUserRequestDTO;
import com.hahn.ticketsystem.dtos.AppUserResponseDTO;
import com.hahn.ticketsystem.entities.AppUser;
import com.hahn.ticketsystem.repositories.AppUserRepository;
import com.hahn.ticketsystem.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class SecurityController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    AppUserService appUserService;

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }




    @PostMapping("/login")
    public AuthResponse login(@RequestBody AppUserRequestDTO appUserRequestDTO){
        System.out.println(appUserRequestDTO.getEmail());
        System.out.println(appUserRequestDTO.getPassword());


        String email = appUserRequestDTO.getEmail();
        String password = appUserRequestDTO.getPassword();

        Authentication authentication = this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password)
        );




        Instant instant = Instant.now();
        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));


        JwtClaimsSet jwtClaimsSet = JwtClaimsSet
                .builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(email)
                .claim("scope", scope)
                .build();


        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );

        String jwt = this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        //return Map.of("token", jwt);

        AuthResponse authResponse= new AuthResponse();

        AppUserResponseDTO appUserResponseDTO = appUserService.getAppUserResponseDTO(email).orElse(null);

        authResponse.setToken(jwt);
        authResponse.setUser(appUserResponseDTO);

        System.out.println(authResponse);

        return authResponse;

    }


}
