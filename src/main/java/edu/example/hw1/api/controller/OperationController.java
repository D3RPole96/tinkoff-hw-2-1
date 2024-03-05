package edu.example.hw1.api.controller;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.api.mapper.OperationMapper;
import edu.example.hw1.domain.entity.OperationEntity.OperationType;
import edu.example.hw1.domain.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;
    private final OperationMapper operationMapper;

    @GetMapping("/{type}")
    public List<OperationDto> getOperations(@PathVariable String type) {
        var mappedType = OperationType.valueOf(type);
        return operationMapper.operationEntitiesToOperationDtos(operationService.getOperationsByType(mappedType));
    }
}
