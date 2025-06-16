package com.fastcampus.sparta09projectboard.domain;

import java.time.LocalDateTime;

public class Article {

  private Long id;
  private String title; // 제목
  private String content; // 본문
  private String author; // 작성자
  private String password; // 글 비번

  private LocalDateTime createdAt; // 생성일시
  private LocalDateTime updatedAt; // 수정일시
  private String createdBy; // 생성자
  private String updatedBy; // 수정자
}
