package com.example.palisis.infrastructure.persistence.repository;

import com.example.palisis.infrastructure.persistence.entity.OperationLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLineRepositoryJpa extends JpaRepository<OperationLineEntity, Long> {

}
