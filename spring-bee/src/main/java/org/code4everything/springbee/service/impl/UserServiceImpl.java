package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.google.common.base.Strings;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.constant.BeeErrorConsts;
import org.code4everything.springbee.dao.UserDAO;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.RegisterVO;
import org.code4everything.springbee.model.UserInfoVO;
import org.code4everything.springbee.service.CommonService;
import org.code4everything.springbee.service.UserService;
import org.code4everything.springbee.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018/9/15
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final RedisTemplate<String, User> userRedisTemplate;

    private final CommonService commonService;

    private ThreadLocal<String> tokenLocal = new ThreadLocal<>();

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RedisTemplate<String, User> userRedisTemplate,
                           CommonService commonService) {
        this.userDAO = userDAO;
        this.userRedisTemplate = userRedisTemplate;
        this.commonService = commonService;
    }

    @Override
    @LogMethod("更新邮箱")
    public void updateEmail(User user, String email) {
        if (email.equals(user.getEmail())) {
            return;
        }
        Checker.checkEmail(commonService, email);
        user.setEmail(email);
        save(user, true);
    }

    @Override
    @LogMethod("更新用户名")
    public void updateUsername(User user, String username) {
        if (StrUtil.isBlank(username) || user.getUsername().equals(username)) {
            return;
        }
        Checker.checkUsername(commonService, username);
        user.setUsername(username);
        save(user, true);
    }

    @Override
    @LogMethod("更新用户头像")
    public void updateAvatar(User user, String avatar) {
        user.setAvatar(avatar);
        save(user, true);
    }

    @Override
    @LogMethod("更新用户信息")
    public void updateInfo(User user, UserInfoVO userInfoVO) {
        save(userInfoVO.copyInto(user), true);
    }

    @Override
    @LogMethod("注册用户")
    public void register(RegisterVO registerVO) {
        Checker.checkUsername(commonService, registerVO.getUsername());
        Checker.checkEmail(commonService, registerVO.getEmail());
        Checker.checkVerifyCode(registerVO.getEmail(), registerVO.getVcode());
        User user = new User();
        user.setUsername(registerVO.getUsername());
        user.setEmail(registerVO.getEmail());
        user.setPassword(DigestUtil.md5Hex(registerVO.getPassword()));
        user.setCreateTime(System.currentTimeMillis());
        user.setId(IdUtil.simpleUUID());
        user.setStatus("7");
        save(user, false);
    }

    @Override
    @LogMethod("重置密码")
    public void resetPassword(String email, String newPassword) {
        User user = userDAO.getByEmail(email);
        if (ObjectUtil.isNotNull(user)) {
            user.setPassword(DigestUtil.md5Hex(newPassword));
            save(user, false);
        }
    }

    @Override
    @LogMethod("修改密码")
    public boolean updatePassword(User user, String oldPassword, String newPassword) {
        if (user.getPassword().equals(DigestUtil.md5Hex(oldPassword))) {
            user.setPassword(DigestUtil.md5Hex(newPassword));
            save(user, false);
            return true;
        }
        return false;
    }

    @Override
    @LogMethod("用户登录")
    public String login(String loginName, String password) {
        User user = userDAO.getByUsernameOrEmail(loginName, loginName);
        if (ObjectUtil.isNotNull(user) && user.getPassword().equals(DigestUtil.md5Hex(password))) {
            String token = IdUtil.simpleUUID();
            Integer tokenExpired = SpringBeeApplication.getBeeConfigBean().getTokenExpired();
            user.setLoginTime(System.currentTimeMillis());
            save(user, false);
            userRedisTemplate.opsForValue().set(token, user, tokenExpired, TimeUnit.SECONDS);
            return token;
        }
        throw BeeErrorConsts.USERNAME_PASSWORD_INCORRECT;
    }

    @Override
    public User getUserByToken(String token) {
        User user = userRedisTemplate.opsForValue().get(Strings.nullToEmpty(token));
        if (ObjectUtil.isNotNull(user)) {
            // 更新过期时长
            Integer tokenExpired = SpringBeeApplication.getBeeConfigBean().getTokenExpired();
            userRedisTemplate.expire(token, tokenExpired, TimeUnit.SECONDS);
            tokenLocal.set(token);
        }
        return user;
    }

    private void save(User user, boolean update) {
        userDAO.save(user);
        if (update) {
            userRedisTemplate.opsForValue().set(tokenLocal.get(), user);
            tokenLocal.remove();
        }
    }
}
