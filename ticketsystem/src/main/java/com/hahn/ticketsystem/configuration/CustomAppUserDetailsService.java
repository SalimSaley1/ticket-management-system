package com.hahn.ticketsystem.configuration;

import com.hahn.ticketsystem.entities.AppUser;
import com.hahn.ticketsystem.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomAppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user =  this.userRepository.findByEmail(username);
        if(user==null){
            throw new  UsernameNotFoundException("Email ou mot de passe incorrect");
        }
        return new CustomAppUserDetails(user);
    }
}
