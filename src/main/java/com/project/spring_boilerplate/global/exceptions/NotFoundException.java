package com.project.spring_boilerplate.global.exceptions;

public class NotFoundException extends RuntimeException {
   public NotFoundException(String message) {
      super(message);
   }
}
