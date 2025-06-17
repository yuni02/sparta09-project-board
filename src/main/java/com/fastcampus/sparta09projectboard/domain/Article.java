package com.fastcampus.sparta09projectboard.domain;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Table
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
