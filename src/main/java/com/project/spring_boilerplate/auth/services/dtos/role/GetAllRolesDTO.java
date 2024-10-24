package com.project.spring_boilerplate.auth.services.dtos.role;

import java.util.List;

public record GetAllRolesDTO(
      long length,
      List<AuthRoleDTO> roles
) {
}
