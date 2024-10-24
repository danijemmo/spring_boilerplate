package com.project.spring_boilerplate.auth.services.dtos.task;

import com.project.spring_boilerplate.auth.entities.AuthTask;

import java.util.UUID;

public record AuthTaskDTO(
      UUID id,
      String target) {

   public static AuthTaskDTO fromEntity(AuthTask entity) {
      return new AuthTaskDTO(entity.getId(), entity.getTarget());
   }

   public static AuthTask toEntity(AuthTaskDTO dto) {
      return new AuthTask(dto.id, dto.target);
   }
}
