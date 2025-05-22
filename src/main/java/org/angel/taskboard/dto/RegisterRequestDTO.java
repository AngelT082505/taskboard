package org.angel.taskboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8, max = 68)
    private String password;
}
