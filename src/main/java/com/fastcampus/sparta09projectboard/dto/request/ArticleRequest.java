package com.fastcampus.sparta09projectboard.dto.request;

import com.fastcampus.sparta09projectboard.dto.ArticleDto;
import com.fastcampus.sparta09projectboard.dto.UserAccountDto;

public record ArticleRequest(
        String title,
        String content,
        String password
) {

    public static ArticleRequest of(String title, String password, String content) {
        return new ArticleRequest(title, password, content);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                title,
                password,
                content
        );
    }


}
