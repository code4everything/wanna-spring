package org.code4everything.springbee.interceptor;

import cn.hutool.core.util.StrUtil;
import com.zhazhapan.util.Checker;
import org.apache.log4j.Logger;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pantao
 * @since 2018/10/25
 **/
@Component
public class BeeWebInterceptor implements HandlerInterceptor {

    private final Logger logger = Logger.getLogger(BeeWebInterceptor.class);

    private final RedisTemplate<String, User> userRedisTemplate;

    @Autowired
    public BeeWebInterceptor(RedisTemplate<String, User> userRedisTemplate) {
        this.userRedisTemplate = userRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getServletPath();
        // 黑名单拦截
        if (Checker.startsWith(url, SpringBeeApplication.getConfigProperty().getBlackListPrefix())) {
            request.getRequestDispatcher("/error/banned").forward(request, response);
            return false;
        }
        // 白名单，不进行拦截
        if (Checker.startsWith(url, SpringBeeApplication.getConfigProperty().getWhiteListPrefix())) {
            return true;
        }
        // 当URL匹配映射路径并且不是登录页面时进行拦截
        if (Checker.startsWith(url, SpringBeeApplication.getConfigProperty().getInterceptorListPrefix())) {
            String token = request.getHeader("token");
            User user = userRedisTemplate.opsForValue().get(Checker.checkNull(token));
            if (Checker.isNull(user)) {
                // 非法用户，禁止访问
                logger.error(StrUtil.format("auth error, uuid: {}, ip: {}", token, request.getRemoteAddr()));
                request.getRequestDispatcher("/error/auth").forward(request, response);
                return false;
            }
        }
        return true;
    }
}
