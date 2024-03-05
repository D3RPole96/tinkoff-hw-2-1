package edu.example.hw1.domain.service;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.api.mapper.OperationMapper;
import edu.example.hw1.domain.entity.OperationEntity;
import edu.example.hw1.domain.entity.OperationEntity.OperationType;
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
        var operation = operationMapper.operationDtoToOperationEntity(operationDto);
        operationRepository.save(operation);
    }

    @Override
    public List<OperationEntity> getOperationsByType(OperationType type) {
        return operationRepository.findAllByType(type);
    }
}
