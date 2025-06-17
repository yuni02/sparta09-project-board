package com.fastcampus.sparta09projectboard.repository;

import com.fastcampus.sparta09projectboard.config.JpaConfig;
import com.fastcampus.sparta09projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 TEST")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

  private ArticleRepository articleRepository;

  public JpaRepositoryTest(@Autowired ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }



  @DisplayName("select 테스트")
  @Test
  void givenTestData_whenSelecting_thenWorksFine(){
      List<Article> articles = articleRepository.findAll();

      assertThat(articles)
              .isNotNull()
              .hasSize(0);
  }
}
