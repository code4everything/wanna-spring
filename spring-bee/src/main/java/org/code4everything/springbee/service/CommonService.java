package org.code4everything.springbee.service;

import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;

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

    /**
     * 校验验证码是否正确
     *
     * @param email 邮箱
     * @param vcode 验证码
     * @param shouldDelete 校验通过后是否删除验证码
     *
     * @return 是否正确
     */
    boolean isVcodeValidated(String email, String vcode, boolean shouldDelete);

    /**
     * 发送验证码
     *
     * @param email 邮箱
     *
     * @throws MessagingException 可能发生的异常
     */
    @Async
    void sendVcode(String email) throws MessagingException;
}
