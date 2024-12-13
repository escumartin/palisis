package com.example.palisis.application.mapper;

import com.example.palisis.application.dto.OperationLineDTO;
import com.example.palisis.domain.model.OperationLine;
import com.example.palisis.infrastructure.persistence.entity.OperationLineEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationLineMapper {

    OperationLineDTO toDTO(OperationLine operationLine);

    List<OperationLineDTO> toDTOList(List<OperationLine> operationLines);

    OperationLine toDomain(OperationLineEntity entity);

    List<OperationLine> toDomainList(List<OperationLineEntity> entities);

    OperationLine toEntity(OperationLineDTO operationLineDTO);

    OperationLineEntity toEntity(OperationLine domain);

    List<OperationLine> toEntityList(List<OperationLineDTO> operationLineDTOs);

}
