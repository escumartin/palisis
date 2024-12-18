package com.example.palisis.infrastructure.persistence.adapter;

import com.example.palisis.application.mapper.UserMapper;
import com.example.palisis.domain.model.OperationLine;
import com.example.palisis.domain.model.User;
import com.example.palisis.infrastructure.persistence.entity.OperationLineEntity;
import com.example.palisis.infrastructure.persistence.entity.UserEntity;
import com.example.palisis.infrastructure.persistence.repository.OperationLineRepositoryJpa;
import com.example.palisis.infrastructure.persistence.repository.UserRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private UserRepositoryJpa userRepositoryJpa;

    @Mock
    private OperationLineRepositoryJpa operationLineRepositoryJpa;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    private User userDomain;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userDomain = User.builder()
                .id(1L)
                .name("John")
                .lastName("Doe")
                .password("password123")
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .operationLines(List.of(new OperationLine(1L, "Operation 1")))
                .build();

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("John");
        userEntity.setLastName("Doe");
        userEntity.setPassword("password123");
    }

    @Test
    void save_shouldSaveUserSuccessfully_whenOperationLinesExist() {
        OperationLineEntity operationLineEntity = new OperationLineEntity(1L, "Operation 1", null);

        when(userMapper.toEntity(userDomain)).thenReturn(userEntity);
        when(operationLineRepositoryJpa.findById(1L)).thenReturn(Optional.of(operationLineEntity));
        when(userRepositoryJpa.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(userDomain);

        User result = userRepositoryAdapter.save(userDomain);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John");
        verify(userRepositoryJpa).save(userEntity);
        verify(operationLineRepositoryJpa).findById(1L);
    }

    @Test
    void save_shouldThrowException_whenOperationLineDoesNotExist() {
        when(userMapper.toEntity(userDomain)).thenReturn(userEntity);
        when(operationLineRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRepositoryAdapter.save(userDomain))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("OperationLine not found: 1");

        verify(operationLineRepositoryJpa).findById(1L);
        verifyNoInteractions(userRepositoryJpa);
    }

    @Test
    void findAll_shouldReturnListOfUsers() {
        when(userRepositoryJpa.findAll()).thenReturn(List.of(userEntity));
        when(userMapper.toDomainList(any())).thenReturn(List.of(userDomain));

        List<User> result = userRepositoryAdapter.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("John");
        verify(userRepositoryJpa).findAll();
    }

    @Test
    void existsByName_shouldReturnTrueWhenUserExists() {
        when(userRepositoryJpa.existsByName("John")).thenReturn(true);

        boolean exists = userRepositoryAdapter.existsByName("John");

        assertThat(exists).isTrue();
        verify(userRepositoryJpa).existsByName("John");
    }

    @Test
    void findById_shouldReturnUserWhenUserExists() {
        when(userRepositoryJpa.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity)).thenReturn(userDomain);

        Optional<User> result = userRepositoryAdapter.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John");
        verify(userRepositoryJpa).findById(1L);
    }

    @Test
    void deleteById_shouldDeleteUserSuccessfully() {
        userRepositoryAdapter.deleteById(1L);

        verify(userRepositoryJpa).deleteById(1L);
    }
}
