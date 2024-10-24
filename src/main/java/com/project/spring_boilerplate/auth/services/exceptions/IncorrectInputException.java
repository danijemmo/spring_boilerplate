package com.project.spring_boilerplate.auth.services.exceptions;

public class IncorrectInputException extends RuntimeException {
   public IncorrectInputException(String message) {
      super(message);
   }
}
