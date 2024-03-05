package edu.example.hw1.api.mapper;

import edu.example.hw1.api.dto.ImageDto;
import edu.example.hw1.domain.entity.ImageEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto imageEntityToImageDto(ImageEntity imageEntity);
    List<ImageDto> imageEntitiesToImageDtos(List<ImageEntity> imageEntities);
}
