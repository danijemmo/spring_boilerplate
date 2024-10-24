package com.project.spring_boilerplate.auth.services.dtos.authUser;

import java.util.List;

public record AddRolesRequestDTO(
      List<Long> roles
) {
}
