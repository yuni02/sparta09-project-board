package com.fastcampus.sparta09projectboard.repository;

import com.fastcampus.sparta09projectboard.domain.UserAccount;
import com.fastcampus.sparta09projectboard.domain.projection.UserAccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = UserAccountProjection.class)
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {}
