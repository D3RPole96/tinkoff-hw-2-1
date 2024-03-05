package edu.example.hw1.domain.service;

import edu.example.hw1.domain.entity.ImageEntity;
import edu.example.hw1.domain.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getUserById(Integer id);
    UserEntity getUserByUsername(String username);
    List<UserEntity> getAllUsers();
    UserEntity addNewUser(UserEntity user);
    UserEntity deleteUserById(Integer id);
    UserEntity deleteUserByUsername(String username);
}
