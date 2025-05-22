package org.angel.taskboard.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   private LocalDateTime date;

   private LocalDateTime dateLastAction;

   @Column(nullable = false)
   private String token;

   private String ip;
   private Boolean active;


   public boolean isActive() {
      return active != null && active;
   }

}
