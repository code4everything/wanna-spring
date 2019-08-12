package org.code4everything.springbee.config;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.base.Strings;
import org.code4everything.boot.web.http.HttpUtils;
import org.code4everything.boot.web.mvc.DefaultExceptionHandler;
import org.code4everything.boot.web.mvc.DefaultWebInterceptor;
import org.code4everything.boot.web.mvc.PathFilterHandler;
import org.code4everything.boot.web.mvc.exception.ExceptionFactory;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/20
 **/
@Configuration
public class BeeWebMvcConfiguration implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeeWebMvcConfiguration.class);

    private final UserService userUserService;

    @Autowired
    public BeeWebMvcConfiguration(UserService userUserService) {this.userUserService = userUserService;}

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
        // 添加异常信息
        handler.addException(400, "参数校验失败", HttpStatus.BAD_REQUEST, MethodArgumentNotValidException.class);
        resolvers.add(handler);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".html");
        registry.viewResolver(viewResolver);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("/index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultWebInterceptor(new PathFilterHandler() {

            @Override
            public String buildCacheKey(HttpServletRequest request) {
                return Strings.nullToEmpty(HttpUtils.getToken(request)) + "." + request.getRequestURI();
            }

            @Override
            public void handleBlackList(HttpServletRequest request, HttpServletResponse response, Object handler) {
                throw ExceptionFactory.urlForbade();
            }

            @Override
            public boolean handleInterceptList(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) {
                String token = HttpUtils.requireToken(request);
                User user = userUserService.getUserByToken(token);
                if (ObjectUtil.isNull(user)) {
                    LOGGER.error("auth error, token: {}, ip: {}", token, request.getRemoteAddr());
                    throw ExceptionFactory.userNotLoggedIn();
                }
                return true;
            }
        }));
    }
}
