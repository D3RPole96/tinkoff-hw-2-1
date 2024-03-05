package edu.example.hw1.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ImageDto implements Serializable {
    private int id;
    private String name;
    private long size;
    private String link;
}
