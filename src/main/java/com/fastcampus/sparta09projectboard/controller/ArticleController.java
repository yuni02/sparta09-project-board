package com.fastcampus.sparta09projectboard.controller;


import lombok.RequiredArgsConstructor;

import com.fastcampus.sparta09projectboard.dto.response.ArticleResponse;
import com.fastcampus.sparta09projectboard.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
          @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
          ModelMap map
  ) {
   Page<ArticleResponse> articles = articleService.searchArticles(pageable).map(ArticleResponse::from);

   map.addAttribute("articles", articles);
   map.addAttribute("pageable", pageable);  // Pageable 객체 추가

    return "articles/index";
  }

  @GetMapping("/{articleId}")
  public String article(@PathVariable Long articleId, ModelMap map) {
   ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

   map.addAttribute("article", article);
   map.addAttribute("totalCount", articleService.getArticleCount());
    return "articles/detail";
  }
}
