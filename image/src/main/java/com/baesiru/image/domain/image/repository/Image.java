package com.baesiru.image.domain.image.repository;

import com.baesiru.image.domain.image.repository.enums.ImageKind;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String originalName;
    private String serverName;
    private String extension;
    private ImageKind kind;
    private Long productId;
    private Long boardId;
    private LocalDateTime registeredAt;


}
