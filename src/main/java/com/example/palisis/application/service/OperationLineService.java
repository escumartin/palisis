package com.example.palisis.application.service;

import com.example.palisis.application.dto.OperationLineDTO;

import java.util.List;

public interface OperationLineService {

    List<OperationLineDTO> getAllOperationLines();

    OperationLineDTO getOperationLineById(Long id);

    OperationLineDTO createOperationLine(OperationLineDTO operationLineDTO);

    OperationLineDTO updateOperationLine(Long id, OperationLineDTO operationLineDTO);

    void deleteOperationLine(Long id);
}
