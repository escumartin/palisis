package com.example.palisis.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {

    @NotBlank(message = "Name cannot be empty or null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Last name cannot be empty or null")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @NotBlank(message = "Password cannot be empty or null")
    @Size(min = 12, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{12,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character, and be at least 12 characters long")
    private String password;

    @NotEmpty(message = "Operation lines cannot be empty")
    @Size(min = 1, message = "At least one operation line must be provided")
    private List<OperationLineDTO> operationLines;

}
