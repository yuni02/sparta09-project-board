package com.fastcampus.sparta09projectboard.controller;

import com.fastcampus.sparta09projectboard.service.PaginationService;

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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
/articles
/articles/{article-id}
 */
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

 private final PaginationService paginationService;
 private final ArticleService articleService;


  @GetMapping
  public String articles(ModelMap map) {
    map.addAttribute("articles", List.of());
    return "articles/index";
  }

  @GetMapping
  public String articles(
          @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
          ModelMap map
  ) {
   Page<ArticleResponse> articles = articleService.searchArticles(pageable).map(ArticleResponse::from);
   List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

   map.addAttribute("articles", articles);
   map.addAttribute("paginationBarNumbers", barNumbers);

    return "articles/index";
  }

  @GetMapping("/{articleId}")
  public String article(@PathVariable Long articleId, ModelMap map) {
    map.addAttribute("article", "article"); // TODO: 구현시 여기에 실제 데이터 넣어줘야 함.
    return "articles/detail";
  }
}
