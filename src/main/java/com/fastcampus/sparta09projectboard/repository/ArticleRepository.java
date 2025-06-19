package com.fastcampus.sparta09projectboard.repository;

import com.fastcampus.sparta09projectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource// spring data rest 설정 추가
public interface ArticleRepository extends JpaRepository<Article, Long> {}
