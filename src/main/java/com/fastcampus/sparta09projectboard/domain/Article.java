package com.fastcampus.sparta09projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(
    indexes = {
      @Index(columnList = "title"),
      @Index(columnList = "createdAt"),
      @Index(columnList = "createdBy"),
    })
@Entity
public class Article extends AuditingFields {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @ManyToOne(optional = false)
  private UserAccount userAccount; // 유저 정보 (ID)

  @Setter
  @Column(nullable = false)
  private String title; // 제목

  @Setter
  @Column(nullable = false, length = 10000)
  private String content; // 본문


  @Setter
  @Column(nullable = false)
  private String password; // 글 비번

  protected Article() {}

  private Article(UserAccount userAccount, String title, String content,String password) {
    this.userAccount = userAccount;
    this.title = title;
    this.content = content;
    this.password = password;
  }

  public static Article of(UserAccount userAccount, String title, String content,  String password) {
    return new Article(userAccount, title, content, password);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Article article)) return false;
    return id != null && Objects.equals(id, article.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
