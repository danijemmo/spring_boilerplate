package com.project.spring_boilerplate.auth.configurations;

import com.project.spring_boilerplate.auth.entities.AuthUser;
import com.project.spring_boilerplate.auth.services.AuthUserDetailService;
import com.project.spring_boilerplate.auth.services.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

   private final JwtUtils jwtUtils;
   private final AuthUserDetailService authUserDetailService;

   @Override
   protected void doFilterInternal(
         HttpServletRequest request,
         HttpServletResponse response,
         FilterChain filterChain) throws ServletException, IOException {

      final var reqPath = request.getRequestURI();

      final String authHeader = request.getHeader("Authorization");

      if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
         filterChain.doFilter(request, response);
         return;
      }

      final var jwtToken = authHeader.substring(7);
      final var userEmail = jwtUtils.extractUsername(jwtToken);

      if (!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
         final var userDetails = authUserDetailService.loadUserByUsername(userEmail);

         // fetch the current request path to pass it to the authorise method

//         if (jwtUtils.isTokenValid(jwtToken, userDetails)) {

         if (authUserDetailService.authorise((AuthUser) userDetails, reqPath) && jwtUtils.isTokenValid(jwtToken, userDetails)) {
            final var securityContext = SecurityContextHolder.createEmptyContext();
            final var authToken = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(authToken);

            SecurityContextHolder.setContext(securityContext);
         }
      }

      filterChain.doFilter(request, response);
   }
}
