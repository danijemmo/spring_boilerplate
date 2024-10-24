package com.project.spring_boilerplate.auth.services.dtos.role;

import java.util.Set;
import java.util.UUID;

public record AddTasksRequestDTO(
      Set<UUID> tasks
) {
}
