package com.fastcampus.sparta09projectboard.repository;

import com.fastcampus.sparta09projectboard.config.JpaConfig;
import com.fastcampus.sparta09projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ActiveProfiles("testdb")
@DisplayName("JPA 연결 TEST")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

  private ArticleRepository articleRepository;

  JpaRepositoryTest(@Autowired ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @DisplayName("select 테스트")
  @Test
  void givenTestData_whenSelecting_thenWorksFine() {
    List<Article> articles = articleRepository.findAll();

    assertThat(articles).isNotNull().hasSize(1000);
  }

  @DisplayName("insert 테스트")
  @Test
  void givenTestData_whenInserting_thenWorksFine() {

    // Given
    long previousCount = articleRepository.count();
    // When
    Article savedArticle =
        articleRepository.save(Article.of("new article", "new content", "Jimmy", "1234"));

    // Then
    assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
  }

  @DisplayName("update 테스트")
  @Test
  void givenTestData_whenUpdating_thenWorksFine() {

    // Given
    Article article = articleRepository.findById(1L).orElseThrow();
    String updatedContent = "updated content - 1";
    article.setContent(updatedContent);
    // When

    Article savedArticle = articleRepository.saveAndFlush(article); // 이렇게 안하면 돌아감.

    // Then
    assertThat(savedArticle).hasFieldOrPropertyWithValue("content", updatedContent);
  }

  @DisplayName("DELETE 테스트")
  @Test
  void givenTestData_whenDeleting_thenWorksFine() {
    // Given
    Article article = articleRepository.findById(1L).orElseThrow();
    long previousArticleCount = articleRepository.count();

    // When
    articleRepository.delete(article);

    // Then
    assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
  }
}
