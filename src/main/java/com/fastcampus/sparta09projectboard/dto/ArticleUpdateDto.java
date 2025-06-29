package com.fastcampus.sparta09projectboard.dto;

import java.io.Serializable;

public record ArticleUpdateDto(String title, String content, String author) implements Serializable {
    public static ArticleUpdateDto of(String title, String content, String author) {
        return new ArticleUpdateDto(title, content, author);
    }

}
