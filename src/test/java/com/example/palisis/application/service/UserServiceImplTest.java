package com.example.palisis.application.service;


import com.example.palisis.application.dto.OperationLineDTO;
import com.example.palisis.application.dto.UserCreateDTO;
import com.example.palisis.application.dto.UserDTO;
import com.example.palisis.application.dto.UserUpdateDTO;
import com.example.palisis.application.mapper.UserMapper;
import com.example.palisis.domain.model.User;
import com.example.palisis.domain.repository.UserRepository;
import com.example.palisis.infrastructure.exception.CustomIllegalArgumentException;
import com.example.palisis.infrastructure.exception.ResourceNotFoundException;
import com.example.palisis.infrastructure.security.PasswordGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordGeneratorService passwordGeneratorService;

    private UserCreateDTO userCreateDTO;
    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {

        userCreateDTO = UserCreateDTO.builder()
                .name("John")
                .lastName("Doe")
                .operationLines(List.of())
                .build();

        userDTO = UserDTO.builder()
                .id(1L)
                .name("John")
                .lastName("Doe")
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .operationLines(List.of())
                .build();

        user = new User(1L, "John", "Doe", "encodedPassword", LocalDateTime.now(), LocalDateTime.now(), List.of());
    }

    @Test
    void createUser_shouldReturnUserDTO_whenValidInput() {
        when(userRepository.existsByName(any())).thenReturn(false);
        when(passwordGeneratorService.generateSecurePassword(anyInt())).thenReturn("rawPassword");
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userMapper.toDomain(any(UserCreateDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.createUser(userCreateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");

        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_shouldThrowException_whenUserExists() {
        when(userRepository.existsByName(any())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(userCreateDTO))
                .isInstanceOf(CustomIllegalArgumentException.class)
                .hasMessage("User with name 'John' already exists");

        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_shouldReturnUpdatedUserDTO_whenValidInput() {
        // Arrange
        List<OperationLineDTO> operationLineDTOs = List.of(
                OperationLineDTO.builder().id(1L).name("Operation 1").build(),
                OperationLineDTO.builder().id(2L).name("Operation 2").build()
        );

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .name("Updated Name")
                .lastName("Updated Last Name")
                .password("NewPassword123!")
                .operationLines(operationLineDTOs)
                .build();

        User user1 = new User(1L, "John", "Doe", "password1", LocalDateTime.now(), LocalDateTime.now(), List.of());
        UserDTO userDTO1 = new UserDTO(1L, "Updated Name", "Updated Last Name", LocalDateTime.now(), LocalDateTime.now(), operationLineDTOs);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO1);

        UserDTO result = userService.updateUser(1L, userUpdateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getLastName()).isEqualTo("Updated Last Name");
        assertThat(result.getOperationLines()).hasSize(2);
        assertThat(result.getOperationLines().getFirst().getId()).isEqualTo(1L);
        assertThat(result.getOperationLines().getFirst().getName()).isEqualTo("Operation 1");

        verify(userRepository).save(any(User.class));
        verify(userMapper).toDTO(any(User.class));
    }


    @Test
    void updateUser_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .name("Updated Name")
                .lastName("Updated Last Name")
                .password("NewPassword123!")
                .operationLines(List.of())
                .build();

        assertThatThrownBy(() -> userService.updateUser(1L, userUpdateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User with id '1' not found");

        verify(userRepository, never()).save(any());
    }

    @Test
    void getUserById_shouldReturnUserDTO_whenUserExists() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John");

        verify(userRepository).findById(anyLong());
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User with id '1' not found");
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExists() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).deleteById(anyLong());
    }

    @Test
    void deleteUser_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User with id '1' not found");
    }

    @Test
    void getAllUsers_shouldReturnListOfUserDTOs_whenUsersExist() {
        List<User> users = List.of(
                new User(1L, "John", "Doe", "password1", LocalDateTime.now(), LocalDateTime.now(), List.of()),
                new User(2L, "Jane", "Smith", "password2", LocalDateTime.now(), LocalDateTime.now(), List.of())
        );

        UserDTO userDTO1 = UserDTO.builder().name("John").lastName("Doe").build();
        UserDTO userDTO2 = UserDTO.builder().name("Jane").lastName("Smith").build();

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTOList(anyList())).thenReturn(List.of(userDTO1, userDTO2));

        List<UserDTO> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("John");
        assertThat(result.get(1).getName()).isEqualTo("Jane");

        verify(userRepository).findAll();
        verify(userMapper).toDTOList(anyList());
    }

    @Test
    void updateUser_shouldMapOperationLinesCorrectly() {
        List<OperationLineDTO> operationLineDTOs = List.of(
                OperationLineDTO.builder().id(1L).name("Operation 1").build(),
                OperationLineDTO.builder().id(2L).name("Operation 2").build()
        );

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .name("Updated Name")
                .lastName("Updated Last Name")
                .password("NewPassword123!")
                .operationLines(operationLineDTOs)
                .build();

        User originalUser = new User(1L, "John", "Doe", "password1", LocalDateTime.now(), LocalDateTime.now(), List.of());

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(originalUser));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userToSave = invocation.getArgument(0);
            return new User(userToSave.getId(), userToSave.getName(), userToSave.getLastName(), userToSave.getPassword(), LocalDateTime.now(), LocalDateTime.now(), userToSave.getOperationLines());
        });

        when(userMapper.toDTO(any(User.class))).thenAnswer(invocation -> {
            User user1 = invocation.getArgument(0);
            return new UserDTO(user1.getId(), user1.getName(), user1.getLastName(), user1.getCreated(), user1.getLastUpdated(), operationLineDTOs);
        });

        UserDTO result = userService.updateUser(1L, userUpdateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getLastName()).isEqualTo("Updated Last Name");
        assertThat(result.getOperationLines()).hasSize(2);
        assertThat(result.getOperationLines().getFirst().getId()).isEqualTo(1L);
        assertThat(result.getOperationLines().getFirst().getName()).isEqualTo("Operation 1");

        verify(userRepository).save(any(User.class));
        verify(userMapper).toDTO(any(User.class));
    }


}
