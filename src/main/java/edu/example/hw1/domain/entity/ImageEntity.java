package edu.example.hw1.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Entity
@Table(name = "images")
@Accessors(chain = true)
public class ImageEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_seq")
    @SequenceGenerator(name = "images_seq", sequenceName = "image_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    private Long size;

    @Column(name = "link", length = 300)
    private String link;

    @ManyToOne
    @JoinTable(
            name = "users_images",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private UserEntity user;
}
