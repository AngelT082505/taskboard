package org.angel.taskboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class LoginResponseDTO {
    private Long userId;
    private String username;
    private String role;
    private String token;
    private Long sessionId;
    private LocalDateTime sessionStartDate;
}
