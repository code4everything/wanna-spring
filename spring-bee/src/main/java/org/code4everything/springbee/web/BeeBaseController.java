package org.code4everything.springbee.web;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.web.BaseController;
import lombok.Setter;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.domain.User;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018/9/19
 */
@Setter
public class BeeBaseController extends BaseController {

    private RedisTemplate<String, User> userRedisTemplate;

    private User user;

    public BeeBaseController() {}

    public BeeBaseController(boolean checkSensitiveData) {
        super(checkSensitiveData);
    }

    public BeeBaseController(HttpServletRequest request) {
        super(request);
    }

    public BeeBaseController(HttpServletRequest request, boolean checkSensitiveData) {
        super(request, checkSensitiveData);
    }

    public BeeBaseController(HttpServletRequest request, RedisTemplate<String, User> userRedisTemplate) {
        super(request);
        this.userRedisTemplate = userRedisTemplate;
    }

    public BeeBaseController(HttpServletRequest request, RedisTemplate<String, User> userRedisTemplate,
                             boolean checkSensitiveData) {
        super(request, checkSensitiveData);
        this.userRedisTemplate = userRedisTemplate;
    }

    protected String getUserId() {
        return getUser().getId();
    }

    protected User getUser() {
        if (Checker.isNull(user)) {
            // 更新过期时长
            Integer tokenExpired = SpringBeeApplication.getBeeConfigBean().getTokenExpired();
            userRedisTemplate.expire(getToken(), tokenExpired, TimeUnit.SECONDS);
            user = userRedisTemplate.opsForValue().get(getToken());
        }
        return user;
    }
}
