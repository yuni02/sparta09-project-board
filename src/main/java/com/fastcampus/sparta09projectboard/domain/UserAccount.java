package com.fastcampus.sparta09projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields {
    @Id
    @Column(length = 50)
    private String userId;

    @Setter @Column(nullable = false) private String userPassword;
    @Setter @Column(length = 100) private String nickname;

    @Setter @Column(length = 100) private String email;


    protected UserAccount() {}

    private UserAccount(String userId, String userPassword, String email,  String nickname, String createdBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;

        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname) {
        return UserAccount.of(userId, userPassword, email, nickname, null);
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String createdBy) {
        return new UserAccount(userId, userPassword, email, nickname, createdBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }

}