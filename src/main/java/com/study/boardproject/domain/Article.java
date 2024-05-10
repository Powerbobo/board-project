package com.study.boardproject.domain;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = { // 테이블에 인덱스 추가
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity // 엔티티 명시
public class Article extends AuditingFields {

    @Id // PK 필수 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 auto Increment를 걸어주기 위해 사용
    private Long id;

    // @Column(nullable = false) -> not null
    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그

    @ToString.Exclude
    @OrderBy("id") // id 기준으로 정렬
    // 두 테이블을 매핑하기 위해서 작성, 작성하지 않으면 매핑하는 두 테이블을 합쳐서 테이블을 만들어버림
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)  // article 테이블로 부터 온 것이라는 의미
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    // 기본 생성자, Article 클래스는 엔티티이기 때문에 private 을 사용 불가능하고, public 혹은 protected (아무 인지 없는 기본 생성자)를 사용해야 함.
    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 생성자, 도메인 아티클을 생성하고자 할 때 어떤 값을 필요로 하는지 가이드를 줌.
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // 동일성, 동등성 검사를 위해 작성
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
