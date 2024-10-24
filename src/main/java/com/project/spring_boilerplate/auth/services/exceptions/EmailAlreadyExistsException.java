package com.project.spring_boilerplate.auth.services.exceptions;

public class EmailAlreadyExistsException extends Exception {
   public EmailAlreadyExistsException(String email) {
      super(String.format("Email %s already exists", email));
   }
}
