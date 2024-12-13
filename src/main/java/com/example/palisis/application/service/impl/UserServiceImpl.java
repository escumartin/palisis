package com.example.palisis.application.service.impl;

import com.example.palisis.application.dto.UserCreateDTO;
import com.example.palisis.application.dto.UserDTO;
import com.example.palisis.application.dto.UserUpdateDTO;
import com.example.palisis.application.mapper.UserMapper;
import com.example.palisis.application.service.UserService;
import com.example.palisis.domain.model.User;
import com.example.palisis.domain.repository.UserRepository;
import com.example.palisis.infrastructure.exception.CustomIllegalArgumentException;
import com.example.palisis.infrastructure.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        validateUserCreate(userCreateDTO);

        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());

        User user = userMapper.toDomain(userCreateDTO);
        user.setPassword(encodedPassword);
        user.setCreated(LocalDateTime.now());
        user.setLastUpdated(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    @Transactional
    @Override
    public UserDTO updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        User existingUser = findUserById(userId);

        updateUserDetails(existingUser, userUpdateDTO);

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = findUserById(userId);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOList(users);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = findUserById(userId);
        userRepository.deleteById(user.getId());
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found"));
    }

    private void updateUserDetails(User existingUser, UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userUpdateDTO.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        if (userUpdateDTO.getName() != null)
            existingUser.setName(userUpdateDTO.getName());
        if (userUpdateDTO.getLastName() != null)
            existingUser.setLastName(userUpdateDTO.getLastName());

        existingUser.setLastUpdated(LocalDateTime.now());
    }

    private void validateUserCreate(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByName(userCreateDTO.getName())) {
            throw new CustomIllegalArgumentException("User with name '" + userCreateDTO.getName() + "' already exists");
        }
    }
}
