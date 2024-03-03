package edu.example.hw1.api.mapper;

import edu.example.hw1.api.dto.ImageDto;
import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.domain.entity.Image;
import edu.example.hw1.domain.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto imageToImageDto(Image image);
    List<ImageDto> imagesToImageDtos(List<Image> images);
}
