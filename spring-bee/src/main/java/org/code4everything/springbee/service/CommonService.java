package org.code4everything.springbee.service;

import javax.mail.MessagingException;

/**
 * @author pantao
 * @since 2018/9/16
 */
public interface CommonService {

    void sendVcode(String to) throws MessagingException;
}
