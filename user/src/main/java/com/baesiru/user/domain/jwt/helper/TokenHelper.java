package com.baesiru.user.domain.jwt.helper;

import com.baesiru.user.common.errorcode.TokenErrorCode;
import com.baesiru.user.common.exception.token.TokenException;
import com.baesiru.user.common.exception.token.TokenExpiredException;
import com.baesiru.user.common.exception.token.TokenSignatureException;
import com.baesiru.user.domain.jwt.ifs.TokenHelperIfs;
import com.baesiru.user.domain.jwt.model.request.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TokenHelper implements TokenHelperIfs {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${jwt.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        return getTokenDto(data, accessTokenPlusHour);
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        return getTokenDto(data, refreshTokenPlusHour);
    }

    @Override
    public java.util.Map<String, Object> validationTokenWithThrow(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        JwtParser parser = Jwts.parser().verifyWith(key).build();

        try{
            Jws<Claims> result = parser.parseSignedClaims(token);
            return new HashMap<>(result.getPayload());
        }catch (Exception e){
            if (e instanceof SignatureException){
                throw new TokenSignatureException(TokenErrorCode.INVALID_TOKEN,e);
            }else if (e instanceof ExpiredJwtException){
                throw new TokenExpiredException(TokenErrorCode.EXPIRED_TOKEN,e);
            }else {
                throw new TokenException(TokenErrorCode.TOKEN_EXCEPTION,e);
            }
        }
    }

    private TokenDto getTokenDto(Map<String, Object> data, Long refreshTokenPlusHour) {
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
        Date expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String jwtToken = Jwts.builder()
                .signWith(key)
                .claims(data)
                .expiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }


}
