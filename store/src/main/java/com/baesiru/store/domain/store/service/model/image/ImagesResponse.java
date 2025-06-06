package com.baesiru.store.domain.store.service.model.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagesResponse {
    private List<String> serverNames;
}
