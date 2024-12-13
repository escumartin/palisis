package com.example.palisis.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationLineDTO {

    private Long id;

    @NotBlank(message = "Name cannot be empty or null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

}
