package com.project.spring_boilerplate.auth.services;

import com.project.spring_boilerplate.auth.entities.AuthTask;
import com.project.spring_boilerplate.auth.entities.AuthUser;
import com.project.spring_boilerplate.auth.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserDetailService implements UserDetailsService {

   private final AuthUserRepository authUserRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return authUserRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
   }

   public Boolean authorise(AuthUser user, String route) {
      System.out.println("Authorising user: " + user.getEmail() + " for route: " + route);

      if (route.equals("/api/auth/changePassword"))
         return true;

      final var targets = user.getRoles()
            .stream()
            .flatMap(role -> role.getAllowedTasks().stream())
            .map(AuthTask::getTarget)
            .toList();

      final var allowed = targets
            .stream()
            .anyMatch(route::startsWith);

      if (allowed)
         System.out.println("User " + user.getEmail() + " is allowed to access " + route);
      else
         System.out.println("User " + user.getEmail() + " is NOT allowed to access " + route);

      return allowed;
   }
}
