package edu.example.hw1.domain.service;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.domain.entity.OperationEntity;
import edu.example.hw1.domain.entity.OperationEntity.OperationType;

import java.util.List;

public interface OperationService {
    void logOperation(OperationDto operationDto);
    List<OperationEntity> getOperationsByType(OperationType type);
}
