package org.code4everything.springbee.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.code4everything.boot.interfaces.InterceptHandler;
import org.code4everything.boot.web.mvc.DefaultExceptionHandler;
import org.code4everything.boot.web.mvc.DefaultWebInterceptor;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
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

    private static final Logger LOGGER = Logger.getLogger(BeeWebMvcConfiguration.class);

    private final RedisTemplate<String, User> userRedisTemplate;

    @Autowired
    public BeeWebMvcConfiguration(RedisTemplate<String, User> userRedisTemplate) {
        this.userRedisTemplate = userRedisTemplate;
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new DefaultExceptionHandler());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultWebInterceptor(SpringBeeApplication.getBeeConfigBean(),
                new InterceptHandler() {
            @Override
            public void handleBlackList(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                request.getRequestDispatcher("/error/banned").forward(request, response);
            }
            @Override
            public boolean handleInterceptList(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) throws Exception {
                String token = request.getHeader("token");
                User user = userRedisTemplate.opsForValue().get(Strings.nullToEmpty(token));
                if (ObjectUtil.isNull(user)) {
                    // 非法用户，禁止访问
                    LOGGER.error(StrUtil.format("auth error, uuid: {}, ip: {}", token, request.getRemoteAddr()));
                    request.getRequestDispatcher("/error/auth").forward(request, response);
                    return false;
                }
                return true;
            }
        }));
    }
}
