package com.project.spring_boilerplate.auth.services.dtos.authUser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordRequestDTO {
   @NotNull(message = "oldPassword is required")
   @NotEmpty(message = "oldPassword is required")
   public final String oldPassword;

   @NotNull(message = "newPassword is required")
   @NotEmpty(message = "newPassword is required")
   public final String newPassword;
}
