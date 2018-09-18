package org.code4everything.springbee.service;

import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.model.UserInfoDTO;

/**
 * @author pantao
 * @since 2018/9/15
 */
public interface UserService {

    boolean updateInfo(String token, UserInfoDTO userInfoDTO);

    void register(RegisterDTO registerDTO);

    String login(String loginName, String password);
}
