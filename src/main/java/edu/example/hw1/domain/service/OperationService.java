package edu.example.hw1.domain.service;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.domain.entity.Operation;
import edu.example.hw1.domain.entity.Operation.OperationType;

import java.util.List;

public interface OperationService {
    void logOperation(OperationDto operationDto);
    List<Operation> getOperationsByType(OperationType type);
}
