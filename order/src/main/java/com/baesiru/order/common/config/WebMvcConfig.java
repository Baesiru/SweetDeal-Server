package com.baesiru.order.common.config;

import com.baesiru.global.interceptor.AuthorizationInterceptor;
import com.baesiru.global.resolver.AuthenticatedUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    private final List<String> WHITE_LIST = List.of();

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticatedUserResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(WHITE_LIST)
        ;
    }
}
