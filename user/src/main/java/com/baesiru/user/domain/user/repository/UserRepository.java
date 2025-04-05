package com.baesiru.user.domain.user.repository;

import com.baesiru.user.domain.user.repository.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByIdAndStatusOrderByIdDesc(Long userId, UserStatus status);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<User> findFirstByEmailAndStatusNotOrderByEmailDesc(String email, UserStatus status);
    Optional<User> findFirstByEmailAndStatusOrderByIdDesc(String email, UserStatus status);
}
