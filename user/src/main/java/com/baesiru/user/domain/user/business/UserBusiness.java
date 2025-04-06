package com.baesiru.user.domain.user.business;


import com.baesiru.global.annotation.Business;
import com.baesiru.global.api.Api;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.user.common.errorcode.UserErrorCode;
import com.baesiru.user.common.exception.user.LoginFailException;
import com.baesiru.user.common.exception.user.UserUnregisteredException;
import com.baesiru.user.common.response.MessageResponse;
import com.baesiru.user.domain.jwt.model.request.TokenDto;
import com.baesiru.user.domain.jwt.model.response.TokenResponse;
import com.baesiru.user.domain.jwt.service.TokenIssueService;
import com.baesiru.user.domain.user.controller.model.request.*;
import com.baesiru.user.domain.user.controller.model.response.UserInformationResponse;
import com.baesiru.user.domain.user.repository.User;
import com.baesiru.user.domain.user.repository.enums.UserRole;
import com.baesiru.user.domain.user.repository.enums.UserStatus;
import com.baesiru.user.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Business
public class UserBusiness {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenIssueService tokenIssueService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HttpSession httpSession;
    
    @Transactional
    public MessageResponse register(RegisterRequest registerRequest) {
        userService.existsByEmailWithThrow(registerRequest.getEmail());
        userService.existsByNicknameWithThrow(registerRequest.getNickname());

        User user = modelMapper.map(registerRequest, User.class);
        user.setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()));
        userService.register(user);
        MessageResponse messageResponse = new MessageResponse("가입이 완료되었습니다.");
        return messageResponse;
    }

    public MessageResponse duplicationEmailCheck(DuplicationEmailRequest duplicationEmailRequest) {
        userService.existsByEmailWithThrow(duplicationEmailRequest.getEmail());
        MessageResponse messageResponse = new MessageResponse("사용 가능한 이메일입니다.");
        return messageResponse;
    }

    public MessageResponse duplicationNicknameCheck(DuplicationNicknameRequest duplicationNicknameRequest) {
        userService.existsByNicknameWithThrow(duplicationNicknameRequest.getNickname());
        MessageResponse messageResponse = new MessageResponse("사용 가능한 닉네임입니다.");
        return messageResponse;
    }
    
    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        User user = userService.findFirstByEmailAndStatusNotOrderByEmailEsc(email);
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new LoginFailException(UserErrorCode.LOGIN_FAIL);
        }
        user.setLastLoginAt(LocalDateTime.now());
        userService.save(user);
        TokenResponse tokenResponse = tokenIssueService.issueToken(user.getId().toString());
        return tokenResponse;
    }

    public TokenDto reIssueAccessToken(String refreshToken) {
        TokenDto tokenDto = tokenIssueService.reIssueAccessToken(refreshToken);
        return tokenDto;
    }
    
    @Transactional
    public MessageResponse unRegister(UnregisterRequest unregisterRequest, AuthUser authUser) {
        User user = checkUser(unregisterRequest.getPassword(), authUser);
        user.setStatus(UserStatus.UNREGISTERED);
        userService.save(user);
        MessageResponse messageResponse = new MessageResponse("회원탈퇴가 완료되었습니다.");
        return messageResponse;
    }

    public MessageResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, AuthUser authUser) {
        User user = checkUser(updatePasswordRequest.getCurrPassword(), authUser);
        user.setPassword(BCrypt.hashpw(updatePasswordRequest.getNewPassword(), BCrypt.gensalt()));
        userService.save(user);
        MessageResponse messageResponse = new MessageResponse("비밀번호 변경이 완료되었습니다.");
        return messageResponse;
    }

    public UserInformationResponse getUserInformation(AuthUser authUser) {
        User user = userService.findFirstByIdAndStatusOrderByIdDesc(authUser.getUserId());
        UserInformationResponse response = modelMapper.map(user, UserInformationResponse.class);
        return response;
    }

    private User checkUser(String password, AuthUser authUser) {
        User user = userService.findFirstByIdAndStatusOrderByIdDesc(authUser.getUserId());
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new UserUnregisteredException(UserErrorCode.LOGIN_FAIL);
        }
        return user;
    }

    public MessageResponse chanageRole(RoleRequest roleRequest) {
        User user = userService.findFirstByIdAndStatusOrderByIdDesc(roleRequest.getUserId());
        user.setRole(roleRequest.getRole());
        userService.save(user);
        MessageResponse response = new MessageResponse("권한이 수정되었습니다.");
        return response;
    }
}
