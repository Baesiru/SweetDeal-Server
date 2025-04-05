package com.baesiru.user.domain.jwt.model.response;

import com.baesiru.user.domain.jwt.model.request.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private LocalDateTime accessTokenExpiredAt;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiredAt;

    public static TokenResponse of(TokenDto accessToken, TokenDto refreshToken) {
        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .build();
    }
}
