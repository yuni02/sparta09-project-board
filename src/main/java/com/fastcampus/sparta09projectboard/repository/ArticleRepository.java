package com.fastcampus.sparta09projectboard.repository;

import com.fastcampus.sparta09projectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {}
