package edu.example.hw1.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDto {
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String username;

    @NotBlank
    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotBlank
    @NotNull
    @Size(max = 255)
    private String lastName;
}
