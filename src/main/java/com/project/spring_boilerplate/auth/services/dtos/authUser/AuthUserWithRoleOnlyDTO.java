package com.project.spring_boilerplate.auth.services.dtos.authUser;

import com.project.spring_boilerplate.auth.entities.AuthRole;
import com.project.spring_boilerplate.auth.entities.AuthUser;

import java.util.List;
import java.util.UUID;

public record AuthUserWithRoleOnlyDTO(
      UUID id,
      String email,
      String firstName,
      String middleName,
      String lastName,
      List<String> roles
) {
   public static AuthUserWithRoleOnlyDTO fromEntity(AuthUser entity) {
      return new AuthUserWithRoleOnlyDTO(
            entity.getId(),
            entity.getEmail(),
            entity.getFirstName(),
            entity.getMiddleName(),
            entity.getLastName(),
            entity.getRoles().stream().map(AuthRole::getName).toList()
      );
   }
}
