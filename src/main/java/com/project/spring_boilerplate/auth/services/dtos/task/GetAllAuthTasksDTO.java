package com.project.spring_boilerplate.auth.services.dtos.task;

import java.util.List;

public record GetAllAuthTasksDTO(
      long length,
      List<AuthTaskDTO> tasks
) {
}
