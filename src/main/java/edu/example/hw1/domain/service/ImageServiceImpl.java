package edu.example.hw1.domain.service;

import edu.example.hw1.api.dto.OperationDto;
import edu.example.hw1.api.exceptions.EntityNotFoundException;
import edu.example.hw1.domain.entity.Image;
import edu.example.hw1.domain.entity.Operation;
import edu.example.hw1.domain.entity.Operation.OperationType;
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

    @Override
    public boolean existsAll(List<Integer> imagesId) {
        return imageRepository.existsImagesByIdIn(imagesId);
    }

    @Override
    public List<Image> getAllImages(List<Integer> imagesId) {
        return imageRepository.findAllByIdIn(imagesId);
    }

    @Cacheable(value = "ImageService::getImageMeta", key = "#id")
    @Override
    public Image getImageMeta(int id) {
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
    public Image uploadImage(MultipartFile file) throws Exception {
        var image = minioService.uploadImage(file);
        imageRepository.save(image);

        operationService.logOperation(
                new OperationDto(
                        String.format("Upload image: %s", image),
                        LocalDateTime.now(ZoneOffset.UTC).toString(),
                        Operation.OperationType.WRITE.toString()
                )
        );

        return image;
    }
}
