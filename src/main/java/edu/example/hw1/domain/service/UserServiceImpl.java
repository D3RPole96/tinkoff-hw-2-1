package edu.example.hw1.domain.service;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.api.exceptions.EntityNotFoundException;
import edu.example.hw1.domain.entity.Image;
import edu.example.hw1.domain.entity.Operation;
import edu.example.hw1.domain.entity.Operation.OperationType;
import edu.example.hw1.domain.entity.UserEntity;
import edu.example.hw1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OperationService operationService;

    @Cacheable(value = "UserService::getUserById", key="#id")
    @Override
    public UserEntity getUserById(Integer id) {
        var user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным ID не найден"));

        operationService.logOperation(
                new OperationDto(
                        String.format("Read user: %s", user),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.READ.toString()
                )
        );

        return user;
    }

    @Cacheable(value = "UserService::getUserById", key="#username")
    @Override
    public UserEntity getUserByUsername(String username) {
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным именем пользователя не найден"));

        operationService.logOperation(
                new OperationDto(
                        String.format("Read user: %s", user),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.READ.toString()
                )
        );

        return user;
    }

    @Cacheable(value = "UserService::getUserImages", key = "#id + '.images'")
    @Override
    public List<Image> getUserImages(int id) {
        var user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным именем пользователя не найден"));

        var images = user.getImages();

        operationService.logOperation(
                new OperationDto(
                        String.format("Read user images: %s", images),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.READ.toString()
                )
        );

        return images;
    }

    @Cacheable(value = "UserService::getAllUsers")
    @Override
    public List<UserEntity> getAllUsers() {
        var users = userRepository.findAll();

        operationService.logOperation(
                new OperationDto(
                        String.format("Read users: %s", users),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.READ.toString()
                )
        );

        return users;
    }

    @Cacheable(value = "UserService::addNewUser", key = "#user.username")
    @Override
    public UserEntity addNewUser(UserEntity user) {
        user.setCreationTime(LocalDateTime.now(ZoneOffset.UTC));
        var addedUser = userRepository.save(user);

        operationService.logOperation(
                new OperationDto(
                        String.format("Create user: %s", addedUser),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.WRITE.toString()
                )
        );

        return addedUser;
    }

    @Override
    public UserEntity deleteUserById(Integer id) {
        var deletedUser = getUserById(id);
        userRepository.deleteById(id);

        operationService.logOperation(
                new OperationDto(
                        String.format("Deleted user: %s", deletedUser),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.DELETE.toString()
                )
        );

        return deletedUser;
    }

    @Override
    public UserEntity deleteUserByUsername(String username) {
        var deletedUser = getUserByUsername(username);
        userRepository.deleteByUsername(username);

        operationService.logOperation(
                new OperationDto(
                        String.format("Deleted user: %s", deletedUser),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.DELETE.toString()
                )
        );

        return deletedUser;
    }
}
