package com.fastcampus.sparta09projectboard.service;

import com.fastcampus.sparta09projectboard.domain.UserAccount;
import com.fastcampus.sparta09projectboard.dto.ArticleDto;
import com.fastcampus.sparta09projectboard.domain.Article;
import com.fastcampus.sparta09projectboard.dto.ArticleUpdateDto;
import com.fastcampus.sparta09projectboard.dto.request.ArticleRequest;
import com.fastcampus.sparta09projectboard.repository.ArticleRepository;
import com.fastcampus.sparta09projectboard.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public Article searchArticle(long articleId) {
        return articleRepository.findById(articleId).orElse(null);
    }

    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

        Article article = dto.toEntity(userAccount);
        articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(Pageable pageable) {
        return articleRepository.findAll(pageable).map(ArticleDto::from);
    }


    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

            if (article.getUserAccount().equals(userAccount)) {
                if (dto.title() != null) { article.setTitle(dto.title()); }
                if (dto.content() != null) { article.setContent(dto.content()); }

                articleRepository.flush();

            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    public void deleteArticle(long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    public boolean verifyPassword(long articleId, String password) {
        try {
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
            return article.getPassword().equals(password);
        } catch (EntityNotFoundException e) {
            log.warn("게시글을 찾을 수 없습니다 - articleId: {}", articleId);
            return false;
        }
    }

    public void deleteArticleByPassword(long articleId, String password) {
        try {
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
            
            if (article.getPassword().equals(password)) {
                articleRepository.deleteById(articleId);
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 삭제 실패. 게시글을 찾을 수 없습니다 - articleId: {}", articleId);
            throw e;
        }
    }

    public void updateArticleByPassword(Long articleId, ArticleRequest articleRequest, String password) {
        try {
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
            
            if (article.getPassword().equals(password)) {
                if (articleRequest.title() != null) { 
                    article.setTitle(articleRequest.title()); 
                }
                if (articleRequest.content() != null) { 
                    article.setContent(articleRequest.content()); 
                }
                // 비밀번호 변경도 허용
                if (articleRequest.password() != null) {
                    article.setPassword(articleRequest.password());
                }
                
                articleRepository.flush();
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 수정 실패. 게시글을 찾을 수 없습니다 - articleId: {}", articleId);
            throw e;
        }
    }

}
