package com.project.spring_boilerplate.auth.repositories;

import com.project.spring_boilerplate.auth.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
   Optional<AuthUser> findByEmail(String email);
}
