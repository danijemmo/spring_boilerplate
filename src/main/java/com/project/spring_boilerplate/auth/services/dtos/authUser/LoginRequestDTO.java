package com.project.spring_boilerplate.auth.services.dtos.authUser;

public record LoginRequestDTO(
      String email,
      String password
) {
}
