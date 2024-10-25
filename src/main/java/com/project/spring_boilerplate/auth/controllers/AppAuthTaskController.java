package com.project.spring_boilerplate.auth.controllers;

import com.project.spring_boilerplate.auth.services.AuthTaskService;
import com.project.spring_boilerplate.auth.services.dtos.task.AuthTaskDTO;
import com.project.spring_boilerplate.auth.services.dtos.task.CreateAuthTaskDTO;
import com.project.spring_boilerplate.auth.services.dtos.task.GetAllAuthTasksDTO;
import com.project.spring_boilerplate.global.controllers.dtos.APIResponse;
import com.project.spring_boilerplate.global.controllers.validators.ValidUUID;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth/tasks")
@Tags(value = {
      @Tag(name = "Auth Task", description = "Auth Task API")
})
@RequiredArgsConstructor
public class AppAuthTaskController {
   static final Logger logger = LoggerFactory.getLogger(AppAuthTaskController.class);

   private final AuthTaskService authTaskService;

   @GetMapping(value = "/get_all_tasks", produces = "application/json")
   public ResponseEntity<APIResponse<GetAllAuthTasksDTO>> getAllTasks() {
      logger.info("Get all tasks");

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Tasks fetched successfully",
            authTaskService.getAllAuthTasks()
      );
   }

   @GetMapping(value = "/get_a_task/{task_id}", produces = "application/json")
   public ResponseEntity<APIResponse<AuthTaskDTO>> getATask(@PathVariable @Valid @ValidUUID String task_id) {
      logger.info("getATask {}", task_id);

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Task fetched successfully",
            authTaskService.getAuthTask(UUID.fromString(task_id))
      );
   }

   @PostMapping(value = "/create_a_task", produces = "application/json")
   public ResponseEntity<APIResponse<AuthTaskDTO>> createATask(@RequestBody CreateAuthTaskDTO reqdto) {
      logger.info("createATask ");

      return APIResponse.build(
            HttpStatus.CREATED.value(),
            "Task created successfully",
            authTaskService.createAuthTask(reqdto)
      );
   }

   @DeleteMapping(value = "/delete_a_task/{task_id}")
   public ResponseEntity<APIResponse<Void>> deleteATask(@PathVariable @Valid @ValidUUID String task_id){
      logger.info("delete task {}", task_id);

      authTaskService.deleteAuthTask(UUID.fromString(task_id));
      return APIResponse.build(
              HttpStatus.OK.value(),
              "Task fetched successfully",
              null
      );
   }
}
