package edu.example.hw1.domain.service;

import edu.example.hw1.domain.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    ImageEntity uploadImage(MultipartFile file) throws Exception;
    byte[] downloadImage(String link) throws Exception;
}
