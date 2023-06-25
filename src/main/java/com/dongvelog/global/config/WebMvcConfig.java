package com.dongvelog.global.config;

import com.dongvelog.domain.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;


//    프론트에서 CORS 설정을 하기
//    @Override
//    public void addCorsMappings(final CorsRegistry registry) {
//
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedOrigins("http://localhost:5173")
//                ;
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthResolver(sessionRepository, appConfig));
    }
}
