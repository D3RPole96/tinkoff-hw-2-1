package edu.example.hw1.domain.service;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.api.mapper.OperationMapper;
import edu.example.hw1.domain.entity.Operation;
import edu.example.hw1.domain.entity.Operation.OperationType;
import edu.example.hw1.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    @Override
    public void logOperation(OperationDto operationDto) {
        var operation = operationMapper.operationDtoToOperation(operationDto);
        operationRepository.save(operation);
    }

    @Override
    public List<Operation> getOperationsByType(OperationType type) {
        return operationRepository.findAllByType(type);
    }
}
