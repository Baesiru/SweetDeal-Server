package com.baesiru.global.resolver;

import com.baesiru.global.annotation.AuthenticatedUser;
import com.baesiru.global.errorcode.CommonErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
public class AuthenticatedUserResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean annotation = parameter.hasParameterAnnotation(AuthenticatedUser.class);

        boolean parameterType = parameter.getParameterType().equals(AuthUser.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // supportsParameter true 시 실행

        boolean required = parameter.getParameterAnnotation(AuthenticatedUser.class).required();

        // request context holder 에서 user id 찾기
        RequestAttributes requestContext = Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes());

        Object userId = requestContext.getAttribute("x-user-id",
                RequestAttributes.SCOPE_REQUEST);

        if (userId == null && required) {
            throw new RuntimeException(CommonErrorCode.MISSING_REQUIRED_HEADER.getDescription());
        }

        return (userId != null) ? AuthUser.builder().userId(userId.toString()).build() : null;
    }
}
