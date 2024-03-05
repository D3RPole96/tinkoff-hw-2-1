package edu.example.hw1.repository;

import edu.example.hw1.domain.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    boolean existsImagesByIdIn(List<Integer> ids);
    boolean existsImagesByLink(String link);
    Optional<ImageEntity> findImageById(int id);
    List<ImageEntity> findAllByIdIn(List<Integer> ids);
    List<ImageEntity> findAllByUserId(Integer userId);
}
