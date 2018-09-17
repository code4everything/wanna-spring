package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zhazhapan.util.RandomUtils;
import org.code4everything.springbee.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018/9/16
 */
@Service
public class CommonServiceImpl implements CommonService {

    private final JavaMailSender mailSender;

    private final RedisTemplate<String, String> stringRedisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public CommonServiceImpl(JavaMailSender mailSender, RedisTemplate<String, String> stringRedisTemplate) {
        this.mailSender = mailSender;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void sendVcode(String to) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("验证码");
        String vcode = String.valueOf(RandomUtils.getRandomInteger(6));
        helper.setText(StrUtil.format("您的验证码：{}", vcode));
        mailSender.send(mimeMessage);
        stringRedisTemplate.opsForValue().set("vcode:" + to, vcode, 60, TimeUnit.SECONDS);
    }
}
