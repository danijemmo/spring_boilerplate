package com.project.spring_boilerplate.auth.repositories;

import com.project.spring_boilerplate.auth.entities.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {
   Optional<AuthRole> findByName(String name);
}
