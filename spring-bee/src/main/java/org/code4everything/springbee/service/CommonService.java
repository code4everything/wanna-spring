package org.code4everything.springbee.service;

/**
 * @author pantao
 * @since 2018/9/16
 */
public interface CommonService {

    /**
     * 检测用户名是否存在
     *
     * @param username 用户名
     *
     * @return 是否存在
     */
    boolean existsUsername(String username);

    /**
     * 检测邮箱是否存在
     *
     * @param email 邮箱
     *
     * @return 是否存在
     */
    boolean existsEmail(String email);
}
