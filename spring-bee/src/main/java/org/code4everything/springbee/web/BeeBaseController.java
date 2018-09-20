package org.code4everything.springbee.web;

import com.zhazhapan.util.web.BaseController;
import lombok.Setter;
import org.code4everything.springbee.constant.BeeValueConsts;
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

    public BeeBaseController() {}

    public BeeBaseController(HttpServletRequest request) {
        super(request);
    }

    public BeeBaseController(HttpServletRequest request, RedisTemplate<String, User> userRedisTemplate) {
        super(request);
        this.userRedisTemplate = userRedisTemplate;
    }

    protected String getUserId() {
        return getUser().getId();
    }

    protected User getUser() {
        String token = getToken();
        // 更新过期时长
        userRedisTemplate.expire(token, BeeValueConsts.THIRTY_MINUTES, TimeUnit.SECONDS);
        return userRedisTemplate.opsForValue().get(token);
    }
}
