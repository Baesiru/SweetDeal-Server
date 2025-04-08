package com.baesiru.image;

import com.baesiru.image.common.config.FileStorageProperties;
import com.baesiru.image.common.response.MessageResponse;
import com.baesiru.image.domain.image.business.ImageBusiness;
import com.baesiru.image.domain.image.controller.model.request.AssignImageRequest;
import com.baesiru.image.domain.image.controller.model.request.ImageRequest;
import com.baesiru.image.domain.image.controller.model.response.ImageResponse;
import com.baesiru.image.domain.image.repository.Image;
import com.baesiru.image.domain.image.repository.enums.ImageKind;
import com.baesiru.image.domain.image.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageUpdateTest {
    @Mock
    private FileStorageProperties fileStorageProperties;

    @Mock
    private ImageService imageService;

    @Mock
    private ModelMapper modelMapper;

    private ImageBusiness imageBusiness;

    private final Path dummyPath = Path.of("build/test-upload-dir"); // 테스트 전용 폴더

    @BeforeEach
    void setUp() {
        when(fileStorageProperties.getUploadDir()).thenReturn(dummyPath);
        imageBusiness = new ImageBusiness(fileStorageProperties, imageService, modelMapper);
    }

    @Test
    void upload_정상작동() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "image data".getBytes()
        );

        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setFile(multipartFile);

        Image image = Image.builder()
                .originalName("test.jpg")
                .serverName("unique_name.jpg")
                .extension("jpg")
                .imageUrl("some/path/unique_name.jpg")
                .build();

        Image savedImage = Image.builder()
                .originalName("test.jpg")
                .serverName("unique_name.jpg")
                .extension("jpg")
                .imageUrl("some/path/unique_name.jpg")
                .build();

        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setServerName("unique_name.jpg");

        // stubbing
        when(modelMapper.map(imageRequest, Image.class)).thenReturn(image);
        doNothing().when(imageService).save(any(Image.class)); // 수정된 부분
        when(modelMapper.map(any(Image.class), eq(ImageResponse.class))).thenReturn(imageResponse);

        // when
        ImageResponse result = imageBusiness.upload(imageRequest);

        // then
        assertNotNull(result);
        assertEquals("unique_name.jpg", result.getServerName());
        verify(imageService, times(1)).save(any(Image.class));
    }

    @Test
    void updateImage_이미지storeId변경확인() {
        // given
        Long storeId = 1L;

        Image image1 = Image.builder().serverName("image1.jpg").storeId(storeId).build(); // 삭제될 예정
        Image image2 = Image.builder().serverName("image2.jpg").storeId(storeId).build(); // 유지
        List<Image> existingImages = List.of(image1, image2);

        Image newImage3 = Image.builder().serverName("image3.jpg").build(); // 새로 추가될 이미지

        List<String> newServerNames = new ArrayList<>(List.of("image2.jpg", "image3.jpg")); // image1은 빠짐

        when(imageService.findByStoreIdOrderById(storeId)).thenReturn(existingImages);
        when(imageService.findByServerName("image3.jpg")).thenReturn(newImage3);

        doNothing().when(imageService).save(any(Image.class));

        AssignImageRequest assignImageRequest = AssignImageRequest.builder()
                .kind(ImageKind.STORE)
                .storeId(storeId)
                .serverNames(new ArrayList<>(newServerNames))
                .build();

        // when
        //imageBusiness.updateImage(assignImageRequest);

        // then
        verify(imageService).save(argThat(image -> image.getServerName().equals("image1.jpg") && image.getStoreId() == null));
        verify(imageService, never()).save(argThat(image -> image.getServerName().equals("image2.jpg")));
        verify(imageService).save(argThat(image -> image.getServerName().equals("image3.jpg") && image.getStoreId().equals(storeId)));
        verify(imageService, times(2)).save(any(Image.class));
    }
}
