package com.example.palisis.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @NotBlank(message = "Name cannot be empty or null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Last name cannot be empty or null")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @PastOrPresent(message = "Created date must be in the past or present")
    private LocalDateTime created;

    @PastOrPresent(message = "Last updated date must be in the past or present")
    private LocalDateTime lastUpdated;

    @NotBlank(message = "Operation lines cannot be null")
    @Size(min = 1, message = "At least one operation line must be provided")
    private List<Long> operationLineIds;

}
