package edu.example.hw1.api.mapper;

import edu.example.hw1.api.dto.UserCreateDto;
import edu.example.hw1.api.dto.UserResponseDto;
import edu.example.hw1.domain.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDto(UserEntity userEntity);

    UserEntity toUserEntity(UserCreateDto userCreateDto);

    default List<UserResponseDto> toUserResponseDtoList(List<UserEntity> userEntities) {
        return userEntities.stream().map(this::toUserResponseDto).toList();
    }
}
