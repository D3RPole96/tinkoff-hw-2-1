package edu.example.hw1.domain.service;

import edu.example.hw1.domain.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getUserById(Integer id);
    UserEntity getUserByUsername(String username);
    List<UserEntity> getAllUsers();
    UserEntity addNewUser(UserEntity user);
    void deleteUserById(Integer id);
    void deleteUserByUsername(String username);
}
