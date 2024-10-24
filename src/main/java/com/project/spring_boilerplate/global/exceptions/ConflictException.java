package com.project.spring_boilerplate.global.exceptions;

public class ConflictException extends RuntimeException {
   public ConflictException(String message) {
      super(message);
   }
}
