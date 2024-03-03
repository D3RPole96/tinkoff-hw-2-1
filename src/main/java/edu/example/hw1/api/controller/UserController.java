package edu.example.hw1.api.controller;

import edu.example.hw1.api.dto.ImageDto;
import edu.example.hw1.api.dto.UserCreateDto;
import edu.example.hw1.api.dto.UserResponseDto;
import edu.example.hw1.api.mapper.ImageMapper;
import edu.example.hw1.api.mapper.UserMapper;
import edu.example.hw1.domain.entity.UserEntity;
import edu.example.hw1.domain.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Validated
public class UserController {
    private final UserMapper userMapper;
    private final ImageMapper imageMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    @QueryMapping
    public UserResponseDto getUserById(@Argument @PathVariable @NotNull @Min(0) Integer id) {
        return userMapper.toUserResponseDto(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    @QueryMapping
    public UserResponseDto getUserByUsername(@Argument @PathVariable @NotBlank @NotNull @Size(max = 255) String username) {
        return userMapper.toUserResponseDto(userService.getUserByUsername(username));
    }

    @GetMapping()
    @QueryMapping
    public List<UserResponseDto> getAllUsers() {
        return userMapper.toUserResponseDtoList(userService.getAllUsers());
    }

    @GetMapping("/{userId}/images")
    public List<ImageDto> getUserImages(@PathVariable @NotNull @Min(0) Integer userId) {
        return imageMapper.imagesToImageDtos(userService.getUserImages(userId));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @QueryMapping
    public UserResponseDto addNewUser(@Argument @RequestBody @Valid UserCreateDto userCreateDto) {
        var user = userMapper.toUserEntity(userCreateDto);
        return userMapper.toUserResponseDto(userService.addNewUser(user));
    }

    @DeleteMapping("/{id}")
    @QueryMapping
    public UserResponseDto deleteUserById(@Argument @PathVariable @NotNull @Min(0) Integer id) {
        return userMapper.toUserResponseDto(userService.deleteUserById(id));
    }

    @DeleteMapping("/username/{username}")
    @QueryMapping
    public UserResponseDto deleteUserByUsername(@Argument @PathVariable @NotBlank @NotNull @Size(max = 255) String username) {
        return userMapper.toUserResponseDto(userService.deleteUserByUsername(username));
    }
}
