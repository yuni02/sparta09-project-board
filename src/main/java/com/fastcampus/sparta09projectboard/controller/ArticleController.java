package com.fastcampus.sparta09projectboard.controller;

import com.fastcampus.sparta09projectboard.domain.constant.FormStatus;
import com.fastcampus.sparta09projectboard.dto.request.ArticleRequest;
import com.fastcampus.sparta09projectboard.dto.security.BoardPrincipal;
import lombok.RequiredArgsConstructor;

import com.fastcampus.sparta09projectboard.dto.response.ArticleResponse;
import com.fastcampus.sparta09projectboard.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
/articles
/articles/{article-id}
 */
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

  private final ArticleService articleService;

  @GetMapping
  public String articles(
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
          Pageable pageable,
      ModelMap map) {
    Page<ArticleResponse> articles =
        articleService.searchArticles(pageable).map(ArticleResponse::from);

    map.addAttribute("articles", articles);
    map.addAttribute("pageable", pageable); // Pageable 객체 추가

    return "articles/index";
  }

  @GetMapping("/{articleId}")
  public String article(@PathVariable Long articleId, ModelMap map) {
    ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

    map.addAttribute("article", article);
    map.addAttribute("totalCount", articleService.getArticleCount());
    return "articles/detail";
  }

  @GetMapping("/form")
  public String articleForm(ModelMap map) {
    map.addAttribute("formStatus", FormStatus.CREATE);

    return "articles/form";
  }

  @PostMapping("/form")
  public String postNewArticle(
      @AuthenticationPrincipal BoardPrincipal boardPrincipal, ArticleRequest articleRequest) {
    articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));

    return "redirect:/articles";
  }

  @GetMapping("/{articleId}/form")
  public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
    ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

    map.addAttribute("article", article);
    map.addAttribute("formStatus", FormStatus.UPDATE);

    return "articles/form";
  }

  @PostMapping("/{articleId}/form")
  public String updateArticle(
      @PathVariable Long articleId,
      @AuthenticationPrincipal BoardPrincipal boardPrincipal,
      ArticleRequest articleRequest) {
    articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));

    return "redirect:/articles/" + articleId;
  }

  @PostMapping("/{articleId}/delete")
  public String deleteArticle(
      @PathVariable Long articleId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
    articleService.deleteArticle(articleId, boardPrincipal.getUsername());

    return "redirect:/articles";
  }
}
