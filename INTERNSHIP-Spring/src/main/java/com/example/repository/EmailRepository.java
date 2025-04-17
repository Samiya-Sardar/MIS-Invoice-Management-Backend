package com.example.repository;

import com.example.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByFullnameAndEmail(String fullname, String email);
}