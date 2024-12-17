package com.example.palisis.infrastructure.persistence.adapter;

import com.example.palisis.application.mapper.UserMapper;
import com.example.palisis.domain.model.User;
import com.example.palisis.domain.repository.UserRepository;
import com.example.palisis.infrastructure.persistence.entity.UserEntity;
import com.example.palisis.infrastructure.persistence.repository.UserRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedUserEntity = userRepositoryJpa.save(userEntity);
        return userMapper.toDomain(savedUserEntity);
    }

    @Override
    public List<User> findAll() {
        return userMapper.toDomainList(userRepositoryJpa.findAll());
    }

    @Override
    public boolean existsByName(String name) {
        return userRepositoryJpa.existsByName(name);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepositoryJpa.findById(id).map(userMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        userRepositoryJpa.deleteById(id);
    }
}

