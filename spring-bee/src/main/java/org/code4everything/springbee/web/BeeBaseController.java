package org.code4everything.springbee.web;

import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.domain.User;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018/9/19
 */
public class BeeBaseController extends BaseController {

    private RedisTemplate<String, User> userRedisTemplate;

    private User user;

    public BeeBaseController() {}

    //    public BeeBaseController(boolean checkSensitiveData) {
    //        super(checkSensitiveData);
    //    }
    //
    //    public BeeBaseController(HttpServletRequest request) {
    //        super(request);
    //    }
    //
    //    public BeeBaseController(HttpServletRequest request, boolean checkSensitiveData) {
    //        super(request, checkSensitiveData);
    //    }
    //
    //    public BeeBaseController(HttpServletRequest request, RedisTemplate<String, User> userRedisTemplate) {
    //        super(request);
    //        this.userRedisTemplate = userRedisTemplate;
    //    }
    //
    public BeeBaseController(RedisTemplate<String, User> userRedisTemplate) {
        //        super(request, checkSensitiveData);
        this.userRedisTemplate = userRedisTemplate;
    }

    protected String getUserId() {
        return getUser().getId();
    }

    protected User getUser() {
        if (Objects.isNull(user)) {
            // 更新过期时长
            Integer tokenExpired = SpringBeeApplication.getBeeConfigBean().getTokenExpired();
            userRedisTemplate.expire(getToken(), tokenExpired, TimeUnit.SECONDS);
            user = userRedisTemplate.opsForValue().get(getToken());
        }
        return user;
    }
}
