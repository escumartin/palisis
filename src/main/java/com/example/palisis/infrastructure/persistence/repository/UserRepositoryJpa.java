package com.example.palisis.infrastructure.persistence.repository;

import com.example.palisis.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {

    boolean existsByName(String name);
}
