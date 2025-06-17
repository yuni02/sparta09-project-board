package com.fastcampus.sparta09projectboard;

import java.io.Serializable;
import java.time.LocalDateTime;

/** DTO for {@link com.fastcampus.sparta09projectboard.domain.Article} */
public record ArticleDto(
    String title,
    String content,
    String author,
    String password,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String createdBy,
    String updatedBy)
    implements Serializable {}
