package com.fastcampus.sparta09projectboard.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 상대적으로 무거운 테스트
@DisplayName("Data Rest - 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {
  private final MockMvc mvc;

  public DataRestTest(@Autowired MockMvc mvc) {
    this.mvc = mvc;
  }

  @DisplayName("[api] 게시글 리스트 조회")
  @Test
  void givenNothing_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {
    // Given

    // When : 잘 던져지는지만 테스트하기
    mvc.perform(get("/api/articles"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
        .andDo(print());

    // Then
  }

  @DisplayName("[api] 게시글 단건 조회")
  @Test
  void givenNothing_whenRequestingArticles_thenReturnsArticleJsonResponse() throws Exception {
    // Given

    // When : 잘 던져지는지만 테스트하기
    mvc.perform(get("/api/articles/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
            .andDo(print());

    // Then
  }
}
