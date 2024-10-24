package com.project.spring_boilerplate.auth.controllers;

import com.project.spring_boilerplate.auth.entities.AuthUser;
import com.project.spring_boilerplate.auth.services.AuthService;
import com.project.spring_boilerplate.auth.services.dtos.authUser.*;
import com.project.spring_boilerplate.auth.services.exceptions.EmailAlreadyExistsException;
import com.project.spring_boilerplate.global.controllers.dtos.APIResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tags(value = {
      @Tag(name = "Auth", description = "Auth API")
})
@RequiredArgsConstructor
public class AppAuthController {

   private final AuthService authService;


   @PostMapping("/register")
   public ResponseEntity<APIResponse<RegisterResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request)
         throws EmailAlreadyExistsException {
      return APIResponse.build(
            201,
            "User registration successful",
            authService.register(request)
      );
   }

   @PostMapping("/login")
   public ResponseEntity<APIResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request) {
      try {
         return APIResponse.build(
               200,
               "Login successful",
               authService.login(request)
         );
      } catch (BadCredentialsException e) {
         return APIResponse.build(
               400,
               "Invalid credentials",
               null
         );
      } catch (Exception e) {
         return APIResponse.build(
               500,
               e.toString(),
               null
         );
      }
   }

   @PostMapping("/refresh")
   public ResponseEntity<APIResponse<LoginResponseDTO>> refreshToken(@RequestBody TokenRefreshRequest request) {
      try {
         return APIResponse.build(
               200,
               "Refresh token successful",
               authService.refreshToken(request)
         );
      } catch (Exception e) {
         return APIResponse.build(
               500,
               e.toString(),
               null
         );
      }
   }

   @PostMapping("/changePassword")
   public ResponseEntity<APIResponse<Object>> changePassword(@AuthenticationPrincipal AuthUser user, @Valid @RequestBody ChangePasswordRequestDTO request) {
      return APIResponse.build(
            HttpStatus.OK.value(),
            authService.changePassword(user, request),
            null
      );
   }
}
