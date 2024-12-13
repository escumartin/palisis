package com.example.palisis.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDTO {

    @NotBlank(message = "Name cannot be empty or null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Last name cannot be empty or null")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @PastOrPresent(message = "Created date must be in the past or present")
    private LocalDateTime created;

    @NotBlank(message = "Password cannot be empty or null")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character, and be at least 8 characters long")
    private String password;

    @NotEmpty(message = "Operation lines cannot be empty")
    @Size(min = 1, message = "At least one operation line must be provided")
    private List<Long> operationLineIds;

}
