package com.example.palisis.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void shouldCreateUserWithAllFields() {
        LocalDateTime createdTime = LocalDateTime.now();
        LocalDateTime lastUpdatedTime = LocalDateTime.now();

        User user = new User(
                1L,
                "John",
                "Doe",
                "password123",
                createdTime,
                lastUpdatedTime,
                List.of(new OperationLine(1L, "Operation 1"))
        );

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getCreated()).isEqualTo(createdTime);
        assertThat(user.getLastUpdated()).isEqualTo(lastUpdatedTime);
        assertThat(user.getOperationLines()).hasSize(1);
        assertThat(user.getOperationLines().getFirst().getName()).isEqualTo("Operation 1");
    }

    @Test
    void shouldCreateUserUsingBuilder() {
        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .id(2L)
                .name("Jane")
                .lastName("Smith")
                .password("securePassword")
                .created(now)
                .lastUpdated(now)
                .operationLines(List.of(new OperationLine(2L, "Operation 2")))
                .build();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("Jane");
        assertThat(user.getLastName()).isEqualTo("Smith");
        assertThat(user.getPassword()).isEqualTo("securePassword");
        assertThat(user.getCreated()).isEqualTo(now);
        assertThat(user.getLastUpdated()).isEqualTo(now);
        assertThat(user.getOperationLines()).hasSize(1);
        assertThat(user.getOperationLines().getFirst().getName()).isEqualTo("Operation 2");
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        User user = new User();

        LocalDateTime now = LocalDateTime.now();

        user.setId(3L);
        user.setName("Michael");
        user.setLastName("Johnson");
        user.setPassword("newPassword");
        user.setCreated(now);
        user.setLastUpdated(now);
        user.setOperationLines(List.of(new OperationLine(3L, "Operation 3")));

        assertEquals(3L, user.getId());
        assertEquals("Michael", user.getName());
        assertEquals("Johnson", user.getLastName());
        assertEquals("newPassword", user.getPassword());
        assertEquals(now, user.getCreated());
        assertEquals(now, user.getLastUpdated());
        assertNotNull(user.getOperationLines());
        assertEquals(1, user.getOperationLines().size());
        assertEquals("Operation 3", user.getOperationLines().getFirst().getName());
    }

    @Test
    void shouldHandleNullOperationLines() {
        User user = User.builder()
                .id(4L)
                .name("Alice")
                .lastName("Brown")
                .password("nullPassword")
                .operationLines(null)
                .build();

        assertThat(user.getOperationLines()).isNull();
    }

    @Test
    void shouldHandleEmptyOperationLines() {
        User user = User.builder()
                .id(5L)
                .name("Emma")
                .lastName("White")
                .password("emptyPassword")
                .operationLines(List.of())
                .build();

        assertThat(user.getOperationLines()).isEmpty();
    }
}
