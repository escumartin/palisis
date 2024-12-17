package com.example.palisis.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String password;

    private LocalDateTime created;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OperationLineEntity> operationLines = new ArrayList<>();

    public void setOperationLines(List<OperationLineEntity> operationLines) {
        this.operationLines.clear();
        if (operationLines != null) {
            operationLines.forEach(line -> line.setUser(this));
            this.operationLines.addAll(operationLines);
        }
    }
}
