package org.code4everything.springbee.config;

import org.code4everything.springbee.exception.BeeExceptionHandler;
import org.code4everything.springbee.interceptor.BeeWebInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/20
 **/
@Configuration
public class BeeWebMvcConfiguration implements WebMvcConfigurer {

    private final BeeExceptionHandler beeExceptionHandler;

    private final BeeWebInterceptor beeWebInterceptor;

    @Autowired
    public BeeWebMvcConfiguration(BeeExceptionHandler beeExceptionHandler, BeeWebInterceptor beeWebInterceptor) {
        this.beeExceptionHandler = beeExceptionHandler;
        this.beeWebInterceptor = beeWebInterceptor;
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(beeExceptionHandler);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(beeWebInterceptor);
    }
}
