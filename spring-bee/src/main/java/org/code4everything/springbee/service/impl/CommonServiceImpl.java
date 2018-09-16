package org.code4everything.springbee.service.impl;

import org.code4everything.springbee.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018/9/16
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Value("${spring.mail.username}")
    private static String from;

    private final JavaMailSender mailSender;

    @Autowired
    public CommonServiceImpl(JavaMailSender mailSender) {this.mailSender = mailSender;}

    @Override
    public void sendVcode(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("send verify code");
        message.setText("your verify code is {}");
        mailSender.send(message);
    }
}
