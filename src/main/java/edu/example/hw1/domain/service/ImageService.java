package edu.example.hw1.domain.service;

import edu.example.hw1.domain.entity.Image;
import jakarta.persistence.Cacheable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public interface ImageService {
    boolean existsAll(List<Integer> imagesId);
    List<Image> getAllImages(List<Integer> imagesId);
    Image getImageMeta(int id);
    byte[] downloadImage(String link) throws Exception;
    Image uploadImage(MultipartFile file) throws Exception;
}
