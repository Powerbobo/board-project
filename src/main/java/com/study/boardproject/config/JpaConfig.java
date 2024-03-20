package com.study.boardproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

// JPA 설정
@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() { // 생성자에 사람 이름을 쓸 예정이므로 String 으로 설정
        return () -> Optional.of("power"); // TODO: 스프링시큐리티로 인증기능을 붙이게 될 떄, 수정 예정
    }
}
