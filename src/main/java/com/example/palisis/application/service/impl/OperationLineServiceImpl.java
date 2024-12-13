package com.example.palisis.application.service.impl;

import com.example.palisis.application.dto.OperationLineDTO;
import com.example.palisis.application.mapper.OperationLineMapper;
import com.example.palisis.application.service.OperationLineService;
import com.example.palisis.domain.model.OperationLine;
import com.example.palisis.domain.repository.OperationLineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OperationLineServiceImpl implements OperationLineService {

    private final OperationLineRepository operationLineRepository;
    private final OperationLineMapper operationLineMapper;

    @Override
    public List<OperationLineDTO> getAllOperationLines() {
        List<OperationLine> operationLines = operationLineRepository.findAll();
        return operationLines.stream().map(operationLineMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public OperationLineDTO getOperationLineById(Long id) {
        OperationLine operationLine = operationLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OperationLine not found"));
        return operationLineMapper.toDTO(operationLine);
    }

    @Override
    public OperationLineDTO createOperationLine(OperationLineDTO operationLineDTO) {
        OperationLine operationLine = operationLineMapper.toEntity(operationLineDTO);
        OperationLine savedOperationLine = operationLineRepository.save(operationLine);
        return operationLineMapper.toDTO(savedOperationLine);
    }

    @Override
    public OperationLineDTO updateOperationLine(Long id, OperationLineDTO operationLineDTO) {
        OperationLine operationLine = operationLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OperationLine not found"));
        operationLine.setName(operationLineDTO.getName());
        OperationLine updatedOperationLine = operationLineRepository.save(operationLine);
        return operationLineMapper.toDTO(updatedOperationLine);
    }

    @Override
    public void deleteOperationLine(Long id) {
        operationLineRepository.deleteById(id);
    }
}
