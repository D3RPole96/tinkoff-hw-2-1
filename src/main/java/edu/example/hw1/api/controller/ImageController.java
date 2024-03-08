package edu.example.hw1.api.controller;

import edu.example.hw1.api.dto.ImageDto;
import edu.example.hw1.api.mapper.ImageMapper;
import edu.example.hw1.domain.service.ImageService;
import edu.example.hw1.domain.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final JwtService jwtService;

    @PostMapping("/load")
    public ImageDto loadImage(@RequestParam MultipartFile file,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) throws Exception {
        var jwtToken = bearerToken.substring("Bearer ".length());
        var authorUsername = jwtService.getUsernameFromToken(jwtToken);

        return imageMapper.imageEntityToImageDto(imageService.uploadImageToUser(file, authorUsername));
    }

    @GetMapping()
    public List<ImageDto> getAllImages() {
        return imageMapper.imageEntitiesToImageDtos(imageService.getAllImages());
    }

    @GetMapping(value = "/{link}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable String link) throws Exception {
        return imageService.downloadImage(link);
    }

    @GetMapping("/{id}/meta")
    public ImageDto getMeta(@PathVariable int id) {
        return imageMapper.imageEntityToImageDto(imageService.getImageMeta(id));
    }
}
