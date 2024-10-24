package com.project.spring_boilerplate.auth.services.dtos.role;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateRoleDTO(
      @NotNull(message = "Name is required")
      @Size(min = 3, max = 15, message = "Name must be between 3 and 15 characters")
      @Pattern(regexp = "^[a-z0-9_]+$", message = "Name can only contain lower-case letters and underscores")
      String name,
      String description
) {
}
