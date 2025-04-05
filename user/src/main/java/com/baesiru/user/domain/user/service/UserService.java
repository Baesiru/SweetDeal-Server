package com.baesiru.user.domain.user.service;

import com.baesiru.user.common.errorcode.UserErrorCode;
import com.baesiru.user.common.exception.user.EmailExistsException;
import com.baesiru.user.common.exception.user.NicknameExistsException;
import com.baesiru.user.common.exception.user.UserNotFoundException;
import com.baesiru.user.domain.user.controller.model.request.LoginRequest;
import com.baesiru.user.domain.user.repository.User;
import com.baesiru.user.domain.user.repository.UserRepository;
import com.baesiru.user.domain.user.repository.enums.UserRole;
import com.baesiru.user.domain.user.repository.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void register(User user) {
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.REGISTERED);
        user.setRegisteredAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void existsByEmailWithThrow(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (existsByEmail) {
            throw new EmailExistsException(UserErrorCode.EXISTS_USER_EMAIL);
        }
    }

    public void existsByNicknameWithThrow(String nickname) {
        boolean existsByNickname = userRepository.existsByNickname(nickname);
        if (existsByNickname) {
            throw new NicknameExistsException(UserErrorCode.EXISTS_USER_NICKNAME);
        }
    }


    public User findFirstByEmailAndStatusNotOrderByEmailEsc(String email) {
        Optional<User> user = userRepository.findFirstByEmailAndStatusNotOrderByEmailDesc(email, UserStatus.UNREGISTERED);
        if (user.isEmpty()) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }
        return user.get();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findFirstByIdAndStatusOrderByIdDesc(String userId) {
        Optional<User> user = userRepository.findFirstByIdAndStatusOrderByIdDesc(Long.parseLong(userId), UserStatus.REGISTERED);
        if (user.isEmpty()) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }
        return user.get();
    }

}
