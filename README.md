# 게시판 서비스

가장 기본적이고 보편적인 게시판 기능을 둘러볼 수 있는 서비스입니다.
가장 최신의 스프링 부트와 관련 기술들, 자바 17 기능들, 개발 도구들을 경험할 수 있도록 만들어졌습니다.

## 개발 환경

- Intellij IDEA Ultimate
- Java 17
- Gradle 7.4.1
- Spring Boot 2.7.0

## 기술 세부 스택

Spring Boot

- Spring Boot Actuator
- Spring Web
- Spring Data JPA
- Rest Repositories
- Rest Repositories HAL Explorer
- Thymeleaf
- Spring Security
- H2 Database
- MySQL Driver
- Lombok
- Spring Boot DevTools
- Spring Configuration Processor

그 외

- QueryDSL 5.0.0
- Bootstrap 5.2.0-Beta1
- Heroku

# 데모 페이지

- https://sparta09-project-board-28af86744abc.herokuapp.com/

## api 명세서

- [api명세서](https://docs.google.com/spreadsheets/d/1ZDD5eHbfIcoQri37fmk7OmVPeSwSEOyQMG-CVfNH20Q/edit?gid=564540372#gid=564540372)

## 유스케이스

- 게시판 검색은 추후 구현 예정이다.
  ![유즈케이스 다이어그램](/document/use-case.svg)

## ERD

![erd 다이어그램](./document/sparta-board-erd.svg)

## Reference

- 유즈케이스 다이어그램: [#4](https://github.com/djkeh/fastcampus-project-board/issues/4), https://viewer.diagrams.net/#Uhttps%3A%2F%2Fraw.githubusercontent.com%2Fdjkeh%2Ffastcampus-project-board%2Fmain%2Fdocument%2Fuse-case.svg
- API Endpoint 구글 시트: [#1](https://github.com/djkeh/fastcampus-project-board/issues/1), https://docs.google.com/spreadsheets/d/1S2FW7_LlePGF95strSYFJEsEQKoyZ9nGHWM8wZgFmSU/edit?usp=sharing
- 어드민 서비스: https://github.com/djkeh/fastcampus-project-board-admin
