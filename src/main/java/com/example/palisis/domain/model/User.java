package com.example.palisis.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String name;
    private String lastName;
    private String password;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
    private List<OperationLine> operationLines;

}
