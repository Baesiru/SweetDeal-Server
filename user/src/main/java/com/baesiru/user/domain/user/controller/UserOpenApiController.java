package com.baesiru.user.domain.user.controller;

import com.baesiru.global.api.Api;
import com.baesiru.user.common.response.MessageResponse;
import com.baesiru.user.domain.jwt.model.request.TokenDto;
import com.baesiru.user.domain.jwt.model.response.TokenResponse;
import com.baesiru.user.domain.user.business.UserBusiness;
import com.baesiru.user.domain.user.controller.model.request.DuplicationEmailRequest;
import com.baesiru.user.domain.user.controller.model.request.DuplicationNicknameRequest;
import com.baesiru.user.domain.user.controller.model.request.LoginRequest;
import com.baesiru.user.domain.user.controller.model.request.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserOpenApiController {
    @Autowired
    private UserBusiness userBusiness;

    @PostMapping("/register")
    public Api<MessageResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        MessageResponse messageResponse = userBusiness.register(registerRequest);
        return Api.OK(messageResponse);
    }

    @PostMapping("/duplication/email")
    public Api<MessageResponse> duplicationEmailCheck(
            @RequestBody @Valid DuplicationEmailRequest duplicationEmailRequest
    ) {
        MessageResponse messageResponse = userBusiness.duplicationEmailCheck(duplicationEmailRequest);
        return Api.OK(messageResponse);
    }

    @PostMapping("/duplication/nickname")
    public Api<MessageResponse> duplicationNicknameCheck(
            @RequestBody @Valid DuplicationNicknameRequest duplicationNicknameRequest
    ) {
        MessageResponse messageResponse = userBusiness.duplicationNicknameCheck(duplicationNicknameRequest);
        return Api.OK(messageResponse);
    }

    @PostMapping("/login")
    public Api<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        TokenResponse tokenResponse = userBusiness.login(loginRequest);
        return Api.OK(tokenResponse);
    }

    @PostMapping("/reissue")
    public Api<TokenDto> reIssueAccessToken(@RequestHeader("Authorization") String refreshToken) {
        TokenDto response = userBusiness.reIssueAccessToken(refreshToken);
        return Api.OK(response);
    }
}
