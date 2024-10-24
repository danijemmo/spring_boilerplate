package com.project.spring_boilerplate.global.exceptions;

public class NotAuthorizedException extends RuntimeException {
   public NotAuthorizedException() {
      super();
   }

   public NotAuthorizedException(String message) {
      super(message);
   }
}
