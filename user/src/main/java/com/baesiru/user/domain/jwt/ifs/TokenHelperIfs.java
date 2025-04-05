package com.baesiru.user.domain.jwt.ifs;

import com.baesiru.user.domain.jwt.model.request.TokenDto;

import java.util.Map;

public interface TokenHelperIfs {
    TokenDto issueAccessToken(Map<String, Object> data);
    TokenDto issueRefreshToken(Map<String, Object> data);
    Map<String, Object> validationTokenWithThrow(String token);
}
