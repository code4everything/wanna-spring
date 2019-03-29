package org.code4everything.springbee.config;

import cn.hutool.core.util.ObjectUtil;
import org.code4everything.boot.interfaces.InterceptHandler;
import org.code4everything.boot.web.HttpUtils;
import org.code4everything.boot.web.mvc.DefaultExceptionHandler;
import org.code4everything.boot.web.mvc.DefaultWebInterceptor;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultWebInterceptor(new InterceptHandler() {

            @Override
            public void handleBlackList(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                request.getRequestDispatcher("/error/banned").forward(request, response);
            }

            @Override
            public boolean handleInterceptList(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) throws Exception {
                String token = HttpUtils.getToken(request);
                User user = userUserService.getUserByToken(token);
                if (ObjectUtil.isNull(user)) {
                    // 非法用户，禁止访问
                    LOGGER.error("auth error, token: {}, ip: {}", token, request.getRemoteAddr());
                    request.getRequestDispatcher("/error/auth").forward(request, response);
                    return false;
                }
                return true;
            }
        }));
    }
}
