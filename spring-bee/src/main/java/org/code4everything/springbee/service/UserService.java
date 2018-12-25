package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.model.UserInfoDTO;

/**
 * @author pantao
 * @since 2018/9/15
 */
public interface UserService extends org.code4everything.boot.service.UserService<User> {

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @param userInfoDTO 用户信息
     *
     * @return 是否更新成功
     */
    boolean updateInfo(User user, UserInfoDTO userInfoDTO);

    /**
     * 注册用户
     *
     * @param registerDTO 注册信息
     */
    void register(RegisterDTO registerDTO);

    /**
     * 重置密码
     *
     * @param email 邮箱
     * @param newPassword 新密码
     */
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
