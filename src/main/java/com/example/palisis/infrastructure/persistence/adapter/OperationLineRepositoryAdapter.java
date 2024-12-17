package com.example.palisis.infrastructure.persistence.adapter;

import com.example.palisis.application.mapper.OperationLineMapper;
import com.example.palisis.domain.model.OperationLine;
import com.example.palisis.domain.repository.OperationLineRepository;
import com.example.palisis.infrastructure.persistence.repository.OperationLineRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OperationLineRepositoryAdapter implements OperationLineRepository {

    private final OperationLineRepositoryJpa operationLineRepositoryJpa;
    private final OperationLineMapper operationLineMapper;

    @Override
    public OperationLine save(OperationLine operationLine) {
        var operationLineEntity = operationLineMapper.toEntity(operationLine);
        var savedEntity = operationLineRepositoryJpa.save(operationLineEntity);
        return operationLineMapper.toDomain(savedEntity);
    }

    @Override
    public List<OperationLine> findAll() {
        return operationLineMapper.toDomainFromEntityList(operationLineRepositoryJpa.findAll());
    }

    @Override
    public Optional<OperationLine> findById(Long id) {
        return operationLineRepositoryJpa.findById(id).map(operationLineMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        operationLineRepositoryJpa.deleteById(id);
    }
}
