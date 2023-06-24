package com.dongvelog.global.config;

import com.dongvelog.domain.session.entity.Session;
import com.dongvelog.domain.session.repository.SessionRepository;
import com.dongvelog.global.config.data.UserSession;
import com.dongvelog.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        final String accessToken = webRequest.getHeader("Authorization");
        if (!StringUtils.hasText(accessToken)) {
            throw new UnauthorizedException();
        }

        //데이터베이스 사용자 확인작업
        //...
        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(UnauthorizedException::new);


        return new UserSession(session.getUser().getId());
    }
}
