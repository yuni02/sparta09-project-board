package com.fastcampus.sparta09projectboard;

import java.io.Serializable;
import java.time.LocalDateTime;

/** DTO for {@link com.fastcampus.sparta09projectboard.domain.Article} */
public record ArticleDto(
    LocalDateTime createdAt,
    String createdBy,
    String title,
    String content,
    String author,
    String password
   )
    implements Serializable {

  public static ArticleDto of(
      LocalDateTime createdAt,
      String createdBy,
      String title,
      String content,
      String author,
      String password) {

    return new ArticleDto(createdAt, createdBy, title, content, author, password);
  }
}
