package edu.example.hw1.domain.service;

import edu.example.hw1.domain.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    boolean existsAll(List<Integer> imagesId);
    List<ImageEntity> getAllImages();
    ImageEntity getImageMeta(int id);
    byte[] downloadImage(String link) throws Exception;
    ImageEntity uploadImageToUser(MultipartFile file, String authorUsername) throws Exception;
    List<ImageEntity> getUserImages(int userId);
}
