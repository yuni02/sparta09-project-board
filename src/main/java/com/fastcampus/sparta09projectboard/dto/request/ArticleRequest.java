package com.fastcampus.sparta09projectboard.dto.request;

import com.fastcampus.sparta09projectboard.dto.ArticleDto;
import com.fastcampus.sparta09projectboard.dto.UserAccountDto;

public record ArticleRequest(
        String title,
        String content,
        String password
) {

    public static ArticleRequest of(String title, String content, String password) {
        return new ArticleRequest(title, content, password);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                password
        );
    }


}
