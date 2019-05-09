package org.code4everything.springbee.service;

import org.code4everything.boot.service.BootUserService;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.RegisterVO;
import org.code4everything.springbee.model.UserInfoVO;
import org.springframework.scheduling.annotation.Async;

/**
 * @author pantao
 * @since 2018/9/15
 */
public interface UserService extends BootUserService<User> {

    void updateEmail(User user, String email);

    void updateUsername(User user, String username);

    void updateAvatar(User user, String avatar);

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @param userInfoVO 用户信息
     *
     */
    void updateInfo(User user, UserInfoVO userInfoVO);

    /**
     * 注册用户
     *
     * @param registerVO 注册信息
     */
    @Async
    void register(RegisterVO registerVO);

    /**
     * 重置密码
     *
     * @param email 邮箱
     * @param newPassword 新密码
     */
    @Async
    void resetPassword(String email, String newPassword);

    /**
     * 更新密码
     *
     * @param user 用户
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     *
     * @return 是否更新成功
     */
    boolean updatePassword(User user, String oldPassword, String newPassword);

    /**
     * 登录
     *
     * @param loginName 登录名
     * @param password 密码
     *
     * @return 登录 Token
     */
    String login(String loginName, String password);
}
