package com.baesiru.user.domain.user.repository;

import com.baesiru.user.domain.user.repository.enums.UserRole;
import com.baesiru.user.domain.user.repository.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 100)
    private String nickname;
    @Column(nullable = false, length = 13)
    private String phone;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    private LocalDateTime registeredAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime unregisteredAt;
}
