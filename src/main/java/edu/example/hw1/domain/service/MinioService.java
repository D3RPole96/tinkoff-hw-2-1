package edu.example.hw1.domain.service;

import edu.example.hw1.domain.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    Image uploadImage(MultipartFile file) throws Exception;
    byte[] downloadImage(String link) throws Exception;
}
