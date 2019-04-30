package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.google.common.base.Strings;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.dao.UserDAO;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.RegisterVO;
import org.code4everything.springbee.model.UserInfoVO;
import org.code4everything.springbee.service.UserService;
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

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RedisTemplate<String, User> userRedisTemplate) {
        this.userDAO = userDAO;
        this.userRedisTemplate = userRedisTemplate;
    }

    @Override
    @LogMethod("更新用户信息")
    public boolean updateInfo(User user, UserInfoVO userInfoVO) {
        user.setNickname(userInfoVO.getNickname());
        user.setBio(userInfoVO.getBio());
        user.setGender(userInfoVO.getGender());
        userDAO.save(user);
        return true;
    }

    @Override
    @LogMethod("注册用户")
    public void register(RegisterVO registerVO) {
        User user = new User();
        user.setUsername(registerVO.getUsername());
        user.setEmail(registerVO.getEmail());
        user.setPassword(DigestUtil.md5Hex(registerVO.getPassword()));
        user.setCreateTime(System.currentTimeMillis());
        user.setId(IdUtil.simpleUUID());
        user.setStatus("7");
        userDAO.save(user);
    }

    @Override
    @LogMethod("重置密码")
    public void resetPassword(String email, String newPassword) {
        User user = userDAO.getByEmail(email);
        if (ObjectUtil.isNotNull(user)) {
            user.setPassword(DigestUtil.md5Hex(newPassword));
            userDAO.save(user);
        }
    }

    @Override
    @LogMethod("修改密码")
    public boolean updatePassword(User user, String oldPassword, String newPassword) {
        if (user.getPassword().equals(DigestUtil.md5Hex(oldPassword))) {
            user.setPassword(DigestUtil.md5Hex(newPassword));
            userDAO.save(user);
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
            userDAO.save(user);
            userRedisTemplate.opsForValue().set(token, user, tokenExpired, TimeUnit.SECONDS);
            return token;
        }
        return null;
    }

    @Override
    public User getUserByToken(String token) {
        User user = userRedisTemplate.opsForValue().get(Strings.nullToEmpty(token));
        if (ObjectUtil.isNotNull(user)) {
            // 更新过期时长
            Integer tokenExpired = SpringBeeApplication.getBeeConfigBean().getTokenExpired();
            userRedisTemplate.expire(token, tokenExpired, TimeUnit.SECONDS);
        }
        return user;
    }
}
