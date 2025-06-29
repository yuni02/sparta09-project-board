package com.fastcampus.sparta09projectboard.dto.response;

import com.fastcampus.sparta09projectboard.dto.ArticleDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        String password,
        LocalDateTime createdAt,
        String email,
        String author
) {

    public static ArticleResponse of(Long id, String title, String content, String password, LocalDateTime createdAt, String email, String author) {
        return new ArticleResponse(id, title, content, password, createdAt, email, author);
    }

    public static ArticleResponse from(ArticleDto dto) {
        String author = dto.userAccountDto().username();
        if (author == null || author.isBlank()) {
            author = dto.userAccountDto().userId();
        }

        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.password(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                author
        );
    }

}
