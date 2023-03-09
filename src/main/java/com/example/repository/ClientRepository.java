package com.example.repository;

import com.example.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUserName(String userName);

    @Query("SELECT c FROM Client c WHERE c.email = ?1")
    Client findByEmail(String email);

    @Query("SELECT c FROM Client c WHERE c.verificationCode = ?1")
    Client findByVerificationCode(String code);

}
