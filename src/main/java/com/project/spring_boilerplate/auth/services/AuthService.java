package com.project.spring_boilerplate.auth.services;

import com.project.spring_boilerplate.auth.entities.AuthUser;
import com.project.spring_boilerplate.auth.repositories.AuthUserRepository;
import com.project.spring_boilerplate.auth.services.dtos.authUser.*;
import com.project.spring_boilerplate.auth.services.exceptions.EmailAlreadyExistsException;
import com.project.spring_boilerplate.auth.services.exceptions.IncorrectInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

   private final AuthUserRepository authUserRepository;
   private final JwtUtils jwtUtils;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authenticationManager;

   public RegisterResponseDTO register(RegisterRequestDTO requestDto)
         throws EmailAlreadyExistsException {
      final var authUser = new AuthUser(
            requestDto.email,
            passwordEncoder.encode(requestDto.password),
            requestDto.firstName,
            requestDto.middleName,
            requestDto.lastName
      );

      try {
         final var sr = authUserRepository.save(authUser);

         if (sr != null && sr.getId() != null) {
//            return new RegisterResponseDTO(
//                  sr.getId(), sr.getEmail(), sr.getFirstName(), sr.getMiddleName(), sr.getLastName());
            return RegisterResponseDTO.fromEntity(sr);
         }
      } catch (DataIntegrityViolationException e) {
         throw new EmailAlreadyExistsException(requestDto.email);
      }

      return null;
   }

   public LoginResponseDTO login(LoginRequestDTO requestDto) {
      authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                  requestDto.email(), requestDto.password()));
      final var authUser = authUserRepository.findByEmail(requestDto.email()).orElseThrow();
      final var token = jwtUtils.generateToken(authUser);
      final var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), authUser);

      return new LoginResponseDTO(
            token,
            refreshToken,
            RegisterResponseDTO.fromEntity(authUser)
      );
   }

   public LoginResponseDTO refreshToken(TokenRefreshRequest requestDto) {
      final var email = jwtUtils.extractUsername(requestDto.token());
      final var authUser = authUserRepository.findByEmail(email).orElseThrow();

      if (jwtUtils.isTokenValid(requestDto.token(), authUser)) {
         final var token = jwtUtils.generateToken(authUser);
         return new LoginResponseDTO(
               token,
               requestDto.token(),
               RegisterResponseDTO.fromEntity(authUser)
         );
      }

      return null;
   }

   public String changePassword(AuthUser authUser, ChangePasswordRequestDTO requestDto) {

      if (passwordEncoder.matches(requestDto.oldPassword, authUser.getPassword())) {
         authUser.setPassword(passwordEncoder.encode(requestDto.newPassword));
         authUserRepository.save(authUser);
         return "Password changed successfully";
      }
      throw new IncorrectInputException("Invalid old password");
   }
}
