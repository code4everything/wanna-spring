package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.model.UserInfoDTO;

/**
 * @author pantao
 * @since 2018/9/15
 */
public interface UserService extends org.code4everything.boot.service.UserService<User> {

    boolean updateInfo(User user, UserInfoDTO userInfoDTO);

    void register(RegisterDTO registerDTO);

    void resetPassword(String email, String newPassword);

    boolean updatePassword(User user, String oldPassword, String newPassword);

    String login(String loginName, String password);
}
