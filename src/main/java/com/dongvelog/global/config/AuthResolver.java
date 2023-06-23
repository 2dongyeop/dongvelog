package com.dongvelog.global.config;

import com.dongvelog.global.config.data.UserSession;
import com.dongvelog.global.exception.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        final String accessToken = webRequest.getParameter("accessToken");
        if (!StringUtils.hasText(accessToken)) {
            throw new UnauthorizedException();
        }

        final UserSession userSession = new UserSession();
        userSession.name = accessToken;
        return userSession;
    }
}
