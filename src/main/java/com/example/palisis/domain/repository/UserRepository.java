package com.example.palisis.domain.repository;

import com.example.palisis.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);

    List<User> findAll();

    boolean existsByName(String name);
}
