package edu.example.hw1.domain.service;

import edu.example.hw1.api.exceptions.EntityNotFoundException;
import edu.example.hw1.domain.entity.UserEntity;
import edu.example.hw1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity getUserById(Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным ID не найден"));
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным именем пользователя не найден"));
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity addNewUser(UserEntity user) {
        user.setCreationTime(LocalDateTime.now(ZoneOffset.UTC));
        return userRepository.save(user);
    }

    @Override
    public UserEntity deleteUserById(Integer id) {
        var deletedUser = getUserById(id);
        userRepository.deleteById(id);

        return deletedUser;
    }

    @Override
    public UserEntity deleteUserByUsername(String username) {
        var deletedUser = getUserByUsername(username);
        userRepository.deleteByUsername(username);

        return deletedUser;
    }
}
