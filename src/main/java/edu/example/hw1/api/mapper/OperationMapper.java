package edu.example.hw1.api.mapper;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.domain.entity.OperationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    List<OperationDto> operationEntitiesToOperationDtos(List<OperationEntity> operationEntities);

    @Mapping(target = "id", expression = "java(null)")
    OperationEntity operationDtoToOperationEntity(OperationDto operationDto);
}
