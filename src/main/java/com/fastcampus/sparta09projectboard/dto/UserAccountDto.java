package com.fastcampus.sparta09projectboard.dto;

import com.fastcampus.sparta09projectboard.domain.UserAccount;
import java.time.LocalDateTime;

public record UserAccountDto(
        String userId,
        String userPassword,
        String email,
        String username,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UserAccountDto of(String userId, String userPassword, String email, String username) {
        return new UserAccountDto(userId, userPassword, email, username,null, null, null, null);
    }

    public static UserAccountDto of(String userId, String userPassword, String email, String username, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(userId, userPassword, email, username,  createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                username
        );
    }

}
