package edu.example.hw1.domain.service;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.api.exceptions.BadRequestException;
import edu.example.hw1.api.exceptions.EntityNotFoundException;
import edu.example.hw1.domain.entity.ImageEntity;
import edu.example.hw1.domain.entity.OperationEntity.OperationType;
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

        if (user.isDeleted()) {
            throw new EntityNotFoundException("Пользователь с указанным ID удален");
        }

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

        if (user.isDeleted()) {
            throw new EntityNotFoundException("Пользователь с указанным именем пользователя удален");
        }

        operationService.logOperation(
                new OperationDto(
                        String.format("Read user: %s", user),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.READ.toString()
                )
        );

        return user;
    }

    @Cacheable(value = "UserService::getAllUsers")
    @Override
    public List<UserEntity> getAllUsers() {
        var users = userRepository.findAllByIsDeletedIsFalse();

        operationService.logOperation(
                new OperationDto(
                        String.format("Read users: %s", users),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.READ.toString()
                )
        );

        return users;
    }

    @Cacheable(value = "UserService::getAllDeletedUsers")
    @Override
    public List<UserEntity> getAllDeletedUsers() {
        var users = userRepository.findAllByIsDeletedIsTrue();

        operationService.logOperation(
                new OperationDto(
                        String.format("Read deleted users: %s", users),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.READ.toString()
                )
        );

        return users;
    }

    @Override
    public UserEntity deleteUserById(Integer id) {
        var deletedUser = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным ID не найден"));

        if (deletedUser.isDeleted()) {
            throw new BadRequestException("Пользователь с указанным ID уже удален");
        }

        deletedUser.setDeleted(true);
        userRepository.save(deletedUser);

        operationService.logOperation(
                new OperationDto(
                        String.format("Deleted user: %s", deletedUser),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.WRITE.toString()
                )
        );

        return deletedUser;
    }

    @Override
    public UserEntity restoreUserById(Integer id) {
        var restoredUser = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным ID не найден"));

        if (!restoredUser.isDeleted()) {
            throw new BadRequestException("Пользователь с указанным ID не удален");
        }

        restoredUser.setDeleted(false);
        userRepository.save(restoredUser);

        operationService.logOperation(
                new OperationDto(
                        String.format("Restored user: %s", restoredUser),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.WRITE.toString()
                )
        );

        return restoredUser;
    }
}
