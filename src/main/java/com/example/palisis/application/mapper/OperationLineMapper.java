package com.example.palisis.application.mapper;

import com.example.palisis.domain.model.OperationLine;
import com.example.palisis.infrastructure.persistence.entity.OperationLineEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationLineMapper {

    OperationLine toDomain(OperationLineEntity entity);

    List<OperationLine> toDomainFromEntityList(List<OperationLineEntity> entities);

    OperationLineEntity toEntity(OperationLine domain);

}
