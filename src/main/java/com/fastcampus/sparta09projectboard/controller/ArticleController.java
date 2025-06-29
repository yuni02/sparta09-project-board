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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  public String article(@PathVariable("articleId") Long articleId, ModelMap map) {
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
      Authentication authentication, ArticleRequest articleRequest) {
    BoardPrincipal boardPrincipal = extractBoardPrincipal(authentication);
    articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));

    return "redirect:/articles";
  }

  @GetMapping("/{articleId}/form")
  public String updateArticleForm(@PathVariable("articleId") Long articleId, 
                                 @RequestParam(value = "password", required = false) String password,
                                 ModelMap map,
                                 RedirectAttributes redirectAttributes) {
    ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));
    
    // 비밀번호가 제공된 경우 검증
    if (password != null) {
        if (!articleService.verifyPassword(articleId, password)) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "redirect:/articles/" + articleId;
        }
        // 비밀번호가 맞으면 수정 폼 표시
        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);
        return "articles/form";
    }
    
    // 비밀번호가 제공되지 않은 경우 상세 페이지로 리다이렉트
    return "redirect:/articles/" + articleId;
  }

  @PostMapping("/{articleId}/form")
  public String updateArticle(
      @PathVariable("articleId") Long articleId,
      @RequestParam("password") String password,
      ArticleRequest articleRequest,
      RedirectAttributes redirectAttributes) {
  
    // 비밀번호 검증
    if (!articleService.verifyPassword(articleId, password)) {
      redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
      return "redirect:/articles/" + articleId;
    }
    
    // 비밀번호 기반으로 게시글 업데이트
    articleService.updateArticleByPassword(articleId, articleRequest, password);
    return "redirect:/articles/" + articleId;
  }

  @PostMapping("/{articleId}/delete")
  public String deleteArticle(
      @PathVariable("articleId") Long articleId, 
      @RequestParam("password") String password,
      RedirectAttributes redirectAttributes) {
    
    if (!articleService.verifyPassword(articleId, password)) {
      redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
      return "redirect:/articles/" + articleId;
    }
    
    articleService.deleteArticleByPassword(articleId, password);
    return "redirect:/articles";
  }

  private BoardPrincipal extractBoardPrincipal(Authentication authentication) {
    Object principal = authentication.getPrincipal();
    
    if (principal instanceof BoardPrincipal boardPrincipal) {
      return boardPrincipal;
    } else if (principal instanceof String username) {
      return BoardPrincipal.of(username, "", "", username);
    } else {
      throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
    }
  }
}
