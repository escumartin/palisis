package com.example.palisis.domain.service;

import com.example.palisis.application.dto.UserCreateDTO;
import com.example.palisis.application.dto.UserDTO;
import com.example.palisis.application.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO createUser(UserCreateDTO userDTO);

    UserDTO updateUser(Long id, UserUpdateDTO userDTO);

    void deleteUser(Long id);
}
