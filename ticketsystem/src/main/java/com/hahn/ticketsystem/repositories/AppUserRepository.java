package com.hahn.ticketsystem.repositories;

import com.hahn.ticketsystem.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);




}
