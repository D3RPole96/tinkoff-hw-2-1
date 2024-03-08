package edu.example.hw1.repository;

import edu.example.hw1.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findAllByIsDeletedIsFalse();
    List<UserEntity> findAllByIsDeletedIsTrue();
}
