package edu.example.hw1.domain.service;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.api.exceptions.EntityNotFoundException;
import edu.example.hw1.domain.entity.ImageEntity;
import edu.example.hw1.domain.entity.OperationEntity;
import edu.example.hw1.domain.entity.OperationEntity.OperationType;
import edu.example.hw1.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final OperationService operationService;
    private final MinioService minioService;
    private final UserService userService;

    @Override
    public boolean existsAll(List<Integer> imagesId) {
        return imageRepository.existsImagesByIdIn(imagesId);
    }

    @Override
    public List<ImageEntity> getAllImages() {
        return imageRepository.findAll();
    }

    @Cacheable(value = "ImageService::getImageMeta", key = "#id")
    @Override
    public ImageEntity getImageMeta(int id) {
        var image = imageRepository
                .findImageById(id)
                .orElseThrow(() -> new EntityNotFoundException("Картинка с указанным ID не найдена"));

        operationService.logOperation(
                new OperationDto(
                        String.format("Read image metadata: %s", image),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.WRITE.toString()
                )
        );

        return image;
    }

    @Override
    public byte[] downloadImage(String link) throws Exception {
        if (!imageRepository.existsImagesByLink(link)) {
            throw new EntityNotFoundException("Картинка с указанной ссылкой не найдена");
        }

        return minioService.downloadImage(link);
    }

    @Cacheable(value = "ImageService::getImageMeta", key = "#file.originalFilename")
    @Override
    public ImageEntity uploadImageToUser(MultipartFile file, String authorUsername) throws Exception {
        var user = userService.getUserByUsername(authorUsername);
        var image = minioService.uploadImage(file);
        image.setUser(user);

        imageRepository.save(image);

        operationService.logOperation(
                new OperationDto(
                        String.format("Upload image: %s, to user %s", image, user.getUsername()),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationEntity.OperationType.WRITE.toString()
                )
        );

        return image;
    }

    @Cacheable(value = "ImageService::getUserImages", key = "#userId + '.images'")
    @Override
    public List<ImageEntity> getUserImages(int userId) {
        var user = userService.getUserById(userId);
        var images = imageRepository.findAllByUserId(userId);

        operationService.logOperation(
                new OperationDto(
                        String.format("Read user's %d images: %s", userId, images),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        OperationType.READ.toString()
                )
        );

        return images;
    }
}
