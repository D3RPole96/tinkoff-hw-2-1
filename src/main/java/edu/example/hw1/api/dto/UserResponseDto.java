package edu.example.hw1.api.dto;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String creationTime;
}
