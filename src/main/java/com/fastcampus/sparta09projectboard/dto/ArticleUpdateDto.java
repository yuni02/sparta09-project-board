package com.fastcampus.sparta09projectboard.dto;

import java.io.Serializable;

public record ArticleUpdateDto(String title, String content) implements Serializable {
    public static ArticleUpdateDto of(String title, String content) {
        return new ArticleUpdateDto(title, content);
    }

}
