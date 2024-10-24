package com.project.spring_boilerplate.auth.controllers;

import com.project.spring_boilerplate.auth.entities.AuthUser;
import com.project.spring_boilerplate.auth.services.AuthUserService;
import com.project.spring_boilerplate.auth.services.dtos.authUser.AddRolesRequestDTO;
import com.project.spring_boilerplate.auth.services.dtos.authUser.AuthUserWithRoleOnlyDTO;
import com.project.spring_boilerplate.auth.services.dtos.authUser.GelAllUsersDTO;
import com.project.spring_boilerplate.auth.services.dtos.authUser.UpdateAuthUserRequestDTO;
import com.project.spring_boilerplate.global.controllers.dtos.APIResponse;
import com.project.spring_boilerplate.global.controllers.validators.ValidUUID;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth/users")
@Tags(value = {
      @Tag(name = "Auth User", description = "Auth User API")
})
@RequiredArgsConstructor
public class AppAuthUserController {
   static final Logger logger = LoggerFactory.getLogger(AppAuthUserController.class);

   final AuthUserService authUserService;

   @GetMapping(value = "/get_all_users", produces = "application/json")
   public ResponseEntity<APIResponse<GelAllUsersDTO>> getAllUsers() {
      return APIResponse.build(
            HttpStatus.OK.value(),
            "All Users fetched successfully",
            authUserService.getAllUsers()
      );
   }

   @PostMapping(value = "/add_roles/{auth_user_id}", produces = "application/json")
   public ResponseEntity<APIResponse<AuthUserWithRoleOnlyDTO>> addRoles(
         @PathVariable @Validated @ValidUUID String auth_user_id, @RequestBody AddRolesRequestDTO reqDTO) {
      logger.info("addRoles for Auth user id: " + auth_user_id);

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Roles added successfully",
            authUserService.addRolesToUser(UUID.fromString(auth_user_id), reqDTO)
      );
   }

   @DeleteMapping(value = "/remove_roles/{auth_user_id}", produces = "application/json")
   public ResponseEntity<APIResponse<AuthUserWithRoleOnlyDTO>> removeRoles(
         @PathVariable @Validated @ValidUUID String auth_user_id, @RequestBody AddRolesRequestDTO reqDTO) {

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Roles are removed successfully",
            authUserService.removeRolesFromUser(UUID.fromString(auth_user_id), reqDTO)
      );
   }


   @GetMapping(value = "/who_am_i", produces = "application/json")
   public ResponseEntity<APIResponse<AuthUserWithRoleOnlyDTO>> whoAmI(
         @AuthenticationPrincipal AuthUser user) {
      return APIResponse.build(
            HttpStatus.OK.value(),
            "You are",
            AuthUserWithRoleOnlyDTO.fromEntity(user)
      );
   }

   @PutMapping(value = "/update_user_information", produces = "application/json")
   public ResponseEntity<APIResponse<AuthUserWithRoleOnlyDTO>> updateUserInfo(
         @AuthenticationPrincipal AuthUser user, @RequestBody @Valid UpdateAuthUserRequestDTO reqDTO) {
      return APIResponse.build(
            HttpStatus.OK.value(),
            "User updated successfully",
            authUserService.updateAuthUserInformation(user, reqDTO)
      );
   }
}
