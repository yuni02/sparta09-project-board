package com.fastcampus.sparta09projectboard.dto.request;

import com.fastcampus.sparta09projectboard.dto.UserAccountDto;

public record SignupRequest(
        String userId,
        String userPassword,
        String email,
        String nickname
) {

    public static SignupRequest of(String userId, String userPassword, String email, String nickname) {
        return new SignupRequest(userId, userPassword, email, nickname);
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                userId,
                userPassword,
                email,
                nickname
        );
    }
} 