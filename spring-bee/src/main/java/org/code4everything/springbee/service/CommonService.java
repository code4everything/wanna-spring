package org.code4everything.springbee.service;

import javax.mail.MessagingException;

/**
 * @author pantao
 * @since 2018/9/16
 */
public interface CommonService {

    boolean existsUsername(String username);

    boolean existsEmail(String email);

    boolean isVcodeValidated(String email, String vcode);

    void sendVcode(String to) throws MessagingException;
}
