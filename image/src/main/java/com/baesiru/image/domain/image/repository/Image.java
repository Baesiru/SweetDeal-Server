package com.baesiru.image.domain.image.repository;

import com.baesiru.image.domain.image.repository.enums.ImageKind;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private ImageKind kind;
    private Long productId;
    private Long storeId;
    private LocalDateTime registeredAt;


}
