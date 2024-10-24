package com.project.spring_boilerplate.auth.entities;

import com.project.spring_boilerplate.global.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "auth_task")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTask extends Auditable {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(nullable = false, unique = true)
   private String target;

   public AuthTask(String target) {
      this.target = target;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      return ((AuthTask) o).target.equals(this.target);
      // return this.id.equals(((AuthTask) o).id);
   }
}
