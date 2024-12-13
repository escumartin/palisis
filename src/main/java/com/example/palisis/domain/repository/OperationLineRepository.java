package com.example.palisis.domain.repository;

import com.example.palisis.domain.model.OperationLine;

import java.util.List;
import java.util.Optional;

public interface OperationLineRepository {

    Optional<OperationLine> findById(Long id);

    OperationLine save(OperationLine operationLine);

    void deleteById(Long id);

    List<OperationLine> findAll();

}
