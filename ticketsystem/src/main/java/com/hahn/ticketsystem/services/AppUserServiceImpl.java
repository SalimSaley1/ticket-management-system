package com.hahn.ticketsystem.services;

import com.hahn.ticketsystem.dtos.AppUserRequestDTO;
import com.hahn.ticketsystem.dtos.AppUserResponseDTO;
import com.hahn.ticketsystem.entities.AppUser;
import com.hahn.ticketsystem.entities.AuditLog;
import com.hahn.ticketsystem.entities.Ticket;
import com.hahn.ticketsystem.enums.Role;
import com.hahn.ticketsystem.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;


    public void initUsers() {

        /*List<Ticket> createdTickets = new ArrayList<>();
        createdTickets.add(null);
        createdTickets.add(null);
        List<AuditLog> performedActions = new ArrayList<>();
        performedActions.add(null);
        performedActions.add(null);

        AppUser user1  = new AppUser();
        user1.setEmail("salim.saleymidou88@gmail.com");
        user1.setPassword("1234");
        user1.setFullName("Salim Saley Midou");
        user1.setRole(Role.EMPLOYEE);
        user1.setCreatedTickets(createdTickets);
        user1.setPerformedActions(performedActions);

        appUserRepository.save(user1);

         */

        List<Ticket> createdTickets1 = new ArrayList<>();
        createdTickets1.add(null);
        createdTickets1.add(null);
        List<AuditLog> performedActions1 = new ArrayList<>();
        performedActions1.add(null);
        performedActions1.add(null);

        AppUser user2  = new AppUser();
        user2.setEmail("bassit@gmail.com");
        user2.setPassword("1234567");
        user2.setFullName("Bassit Laminou");
        user2.setRole(Role.IT_SUPPORT);
        user2.setCreatedTickets(createdTickets1);
        user2.setPerformedActions(performedActions1);

        appUserRepository.save(user2);


    }



    @Override
    public Optional<AppUserResponseDTO> getAppUserResponseDTO(String email) {
        AppUser appUser = this.appUserRepository.findByEmail(email);
        return Optional.ofNullable(appUserToAppUserResponseDTO(appUser));
    }

    @Override
    public AppUser getAppUser(String email) {
        return this.appUserRepository.findByEmail(email);
    }

    public AppUserResponseDTO appUserToAppUserResponseDTO(AppUser appUser) {
        return mapObject(appUser, AppUserResponseDTO.class);
    }
    private static <T> T mapObject(Object source, Class<T> destinationClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(source, destinationClass);
    }

    @Override
    public AppUserResponseDTO save(AppUserRequestDTO appUserRequestDTO) {

        AppUser existingUser = this.appUserRepository.findByEmail(appUserRequestDTO.getEmail());

        if(existingUser==null){

            PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
            appUserRequestDTO.setPassword(passwordEncoder.encode(appUserRequestDTO.getPassword()));

            AppUser appUser = AppUserRequestDTOToAppUser(appUserRequestDTO);


            AppUser savedUser = appUserRepository.save(appUser);

            AppUserResponseDTO appUserResponseDTO = appUserToAppUserResponseDTO(savedUser);
            appUserResponseDTO.setExits(false);

            return  appUserResponseDTO;

        }


            AppUserResponseDTO appUserResponseDTO = appUserToAppUserResponseDTO(existingUser);
            appUserResponseDTO.setExits(true);
            return  appUserResponseDTO;



    }

    AppUser AppUserRequestDTOToAppUser( AppUserRequestDTO appUserRequestDTO){
        return mapObject(appUserRequestDTO, AppUser.class);
    }





}
