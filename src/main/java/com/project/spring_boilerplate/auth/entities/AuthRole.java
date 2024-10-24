package com.project.spring_boilerplate.auth.entities;

import com.project.spring_boilerplate.global.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "auth_role")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRole extends Auditable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true, nullable = true)
   private String name;

   @Column(nullable = true)
   private String description;

   @Setter
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "auth_role_auth_task",
         joinColumns = @JoinColumn(name = "auth_role_id"),
         inverseJoinColumns = @JoinColumn(name = "auth_task_id"))
   private Set<AuthTask> allowedTasks;

   public AuthRole(String name, String description) {
      this.name = name;
      this.description = description;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      return this.id.equals(((AuthRole) o).id);
   }
}
