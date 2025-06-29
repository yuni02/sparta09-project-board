package com.fastcampus.sparta09projectboard.service;

import com.fastcampus.sparta09projectboard.domain.UserAccount;
import com.fastcampus.sparta09projectboard.dto.ArticleDto;
import com.fastcampus.sparta09projectboard.domain.Article;
import com.fastcampus.sparta09projectboard.dto.UserAccountDto;
import com.fastcampus.sparta09projectboard.repository.ArticleRepository;
import com.fastcampus.sparta09projectboard.repository.UserAccountRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

  @InjectMocks private ArticleService sut;

  @Mock private ArticleRepository articleRepository;
  @Mock private UserAccountRepository userAccountRepository;

  /*
    각 게시글 페이지로 이동
  홈 버튼 -> 게시판 페이지로 리다이렉션
  정렬 기능
     */
  @Test
  void given_when_then() {}

  @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
  @Test
  void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
    // Given
    Long articleId = 1L;
    Article article = createArticle();
    given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

    Article searchArticle = sut.searchArticle(articleId);
    // Then
    Assertions.assertThat(searchArticle)
        .hasFieldOrPropertyWithValue("title", article.getTitle())
        .hasFieldOrPropertyWithValue("content", article.getContent());
    then(articleRepository).should().findById(articleId);
  }

  // solitary test (완전한 테스트)
  @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
  @Test
  void givenArticleInfo_whenSaveArticle_thenReturnsArticle() {
    // Given
    ArticleDto dto = createArticleDto();

    given(articleRepository.save(any(Article.class))).willReturn(null);
    // When
    sut.saveArticle(dto);

    // Then
    then(articleRepository).should().save(any(Article.class));
  }

  @DisplayName("게시글 id와 수정 정보를 입력하면, 게시글을 수정한다.")
  @Test
  void givenArticleAndModifiedInfo_whenUpdatingArticle_thenModifiesArticle() {
    // Given
    Article article = createArticle();

    ArticleDto dto = createArticleDto("새 타이틀", "새 내용 #springboot", "1234");
    given(articleRepository.getReferenceById(dto.id())).willReturn(article);
    given(userAccountRepository.getReferenceById(dto.userAccountDto().userId())).willReturn(dto.userAccountDto().toEntity());
    willDoNothing().given(articleRepository).flush();
    given(articleRepository.save(any(Article.class))).willReturn(null);
    // When
    sut.updateArticle(dto.id(), dto);

    // Then
    assertThat(article)
            .hasFieldOrPropertyWithValue("title", dto.title())
            .hasFieldOrPropertyWithValue("content", dto.content());
    then(articleRepository).should().getReferenceById(dto.id());
    then(userAccountRepository).should().getReferenceById(dto.userAccountDto().userId());
    then(articleRepository).should().flush();
  }

  @DisplayName("게시글 작성자가 아닌 사람이 수정 정보를 입력하면, 아무 것도 하지 않는다.")
  @Test
  void givenModifiedArticleInfoWithDifferentUser_whenUpdatingArticle_thenDoesNothing() {
    // Given
    Long differentArticleId = 22L;
    Article differentArticle = createArticle(differentArticleId);
    differentArticle.setUserAccount(createUserAccount("John"));
    ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "1234");
    given(articleRepository.getReferenceById(differentArticleId)).willReturn(differentArticle);
    given(userAccountRepository.getReferenceById(dto.userAccountDto().userId()))
        .willReturn(dto.userAccountDto().toEntity());

    // When
    sut.updateArticle(differentArticleId, dto);

    // Then
    then(articleRepository).should().getReferenceById(differentArticleId);
    then(userAccountRepository).should().getReferenceById(dto.userAccountDto().userId());
  }

  @DisplayName("게시글의 id를 입력하면, 게시글을 삭제한다.")
  @Test
  void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
    // Given
    Article article = createArticle();

    willDoNothing().given(articleRepository).delete(any(Article.class));
    ArticleDto dto = createArticleDto("새 타이틀", "새 내용 #springboot", "1234");

    given(articleRepository.getReferenceById(dto.id())).willReturn(article);
    given(userAccountRepository.getReferenceById(dto.userAccountDto().userId()))
        .willReturn(dto.userAccountDto().toEntity());
    willDoNothing().given(articleRepository).flush();
    // When
    sut.updateArticle(1L, dto);
    Assertions.assertThat(article)
        .hasFieldOrPropertyWithValue("title", dto.title())
        .hasFieldOrPropertyWithValue("content", dto.content())
        .hasFieldOrPropertyWithValue("password", dto.password());
    then(articleRepository).should().delete(any(Article.class));
  }

  private Article createArticle() {
    return createArticle(1L);
  }

  private Article createArticle(Long id) {
    Article article = Article.of(createUserAccount(), "title", "content",  "1234");

    ReflectionTestUtils.setField(article, "id", id);

    return article;
  }

  private UserAccount createUserAccount() {
    return createUserAccount("uno");
  }

  private UserAccount createUserAccount(String userId) {
    return UserAccount.of(userId, "password", "uno@email.com",  null);
  }

  private ArticleDto createArticleDto() {
    return createArticleDto("title", "content", "1234");
  }

  private ArticleDto createArticleDto(
      String title, String content, String password) {
    return ArticleDto.of(
        1L,
        createUserAccountDto(),
        title,
        content,
        password,
        LocalDateTime.now(),
        "Uno",
        LocalDateTime.now(),
        "Uno");
  }

  private UserAccountDto createUserAccountDto() {
    return UserAccountDto.of(
        "uno",
        "password",
        "uno@mail.com",
        LocalDateTime.now(),
        "uno",
        LocalDateTime.now(),
        "uno");
  }
}
