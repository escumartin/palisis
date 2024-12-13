package com.example.palisis.application.mapper;

import com.example.palisis.application.dto.UserCreateDTO;
import com.example.palisis.application.dto.UserDTO;
import com.example.palisis.domain.model.OperationLine;
import com.example.palisis.domain.model.User;
import com.example.palisis.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "operationLineIds", source = "operationLines")
    UserDTO toDTO(User user);

    List<UserDTO> toDTOList(List<User> users);

    User toDomain(UserEntity userEntity);

    @Mapping(target = "operationLines", source = "operationLineIds")
    @Mapping(target = "lastUpdated", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toDomain(UserCreateDTO userEntity);

    List<User> toDomainList(List<UserEntity> userEntities);

    UserEntity toEntity(User user);

    default List<OperationLine> mapToOperationLines(List<Long> ids) {
        return ids.stream().map(id -> OperationLine.builder().id(id).build()).toList();
    }

    default List<Long> mapToOperationLineIds(List<OperationLine> operationLines) {
        return operationLines.stream().map(OperationLine::getId).toList();
    }

}
