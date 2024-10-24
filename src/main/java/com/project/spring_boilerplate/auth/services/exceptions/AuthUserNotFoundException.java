package com.project.spring_boilerplate.auth.services.exceptions;

import java.util.UUID;

public class AuthUserNotFoundException extends RuntimeException {
   public AuthUserNotFoundException(UUID userId) {
      super(String.format("User with id %s is not found", userId.toString()));
   }
}
