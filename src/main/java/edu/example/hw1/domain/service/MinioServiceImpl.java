package edu.example.hw1.domain.service;

import edu.example.hw1.config.MinioProperties;
import edu.example.hw1.domain.entity.Image;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioClient client;
    private final MinioProperties properties;

    @Override
    public Image uploadImage(MultipartFile file) throws Exception {
        var fileId = UUID.randomUUID().toString();

        InputStream inputStream = new ByteArrayInputStream(file.getBytes());
        client.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(fileId)
                        .stream(inputStream, file.getSize(), properties.getImageSize())
                        .contentType(file.getContentType())
                        .build()
        );

        return new Image().setName(file.getOriginalFilename()).setSize(file.getSize()).setLink(fileId);
    }

    @Override
    public byte[] downloadImage(String link) throws Exception {
        return IOUtils.toByteArray(client.getObject(
                GetObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(link)
                        .build()));
    }
}
