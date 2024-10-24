package com.project.spring_boilerplate.auth.repositories;

import com.project.spring_boilerplate.auth.entities.AuthTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuthTaskRepository extends JpaRepository<AuthTask, UUID> {
   AuthTask findByTarget(String target);

   List<AuthTask> findByTargetIn(List<String> targets);
}
