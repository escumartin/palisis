package com.example.palisis.application.mapper;

import com.example.palisis.application.dto.UserCreateDTO;
import com.example.palisis.application.dto.UserDTO;
import com.example.palisis.domain.model.User;
import com.example.palisis.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    List<UserDTO> toDTOList(List<User> users);

    User toDomain(UserEntity userEntity);

    User toDomain(UserCreateDTO userEntity);

    List<User> toDomainList(List<UserEntity> userEntities);

    UserEntity toEntity(User user);

}
