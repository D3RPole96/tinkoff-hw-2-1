package edu.example.hw1.api.mapper;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.domain.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    List<OperationDto> operationsToOperationDtos(List<Operation> operations);

    @Mapping(target = "id", expression = "java(null)")
    Operation operationDtoToOperation(OperationDto operationDto);
}
