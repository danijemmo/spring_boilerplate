package com.project.spring_boilerplate.auth.services.dtos.authUser;

import java.util.List;

public record GelAllUsersDTO(
      int length,
      List<AuthUserWithRoleOnlyDTO> users
) {
}
