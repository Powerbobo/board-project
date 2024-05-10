package com.study.boardproject.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.study.boardproject.domain.Article;
import com.study.boardproject.domain.QArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // Article 안에 있는 모든 필드에 대한 기본 검색 기능 추가
        QuerydslBinderCustomizer<QArticle> {
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) { // 선택적으로 검색 가능하게끔 함
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 검색할 필터 선정
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // 쿼리문 -> like '${v}'
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // 쿼리문 -> like '%${v}%', IgnoreCase -> 대소문자구분
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
