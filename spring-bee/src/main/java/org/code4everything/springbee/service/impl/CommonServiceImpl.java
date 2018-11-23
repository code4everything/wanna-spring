package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Strings;
import org.code4everything.boot.annotations.AopLog;
import org.code4everything.springbee.dao.UserDAO;
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

    private final UserDAO userDAO;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public CommonServiceImpl(JavaMailSender mailSender, RedisTemplate<String, String> stringRedisTemplate,
                             UserDAO userDAO) {
        this.mailSender = mailSender;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userDAO = userDAO;
    }

    @Override
    @AopLog("查看用户名是否存在")
    public boolean existsUsername(String username) {
        return userDAO.countByUsername(username) > 0;
    }

    @Override
    @AopLog("查看邮箱是否存在")
    public boolean existsEmail(String email) {
        return userDAO.countByEmail(email) > 0;
    }

    @Override
    @AopLog("校验验证码是否正确")
    public boolean isVcodeValidated(String email, String vcode, boolean shouldDelete) {
        String key = "vcode:" + email;
        boolean result = Strings.nullToEmpty(vcode).equals(stringRedisTemplate.opsForValue().get(key));
        if (shouldDelete && result) {
            stringRedisTemplate.delete(key);
        }
        return result;
    }

    @Override
    @AopLog("发送验证码")
    public void sendVcode(String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(from);
        helper.setTo(email);
        helper.setSubject("验证码");
        String vcode = RandomUtil.randomNumbers(6);
        helper.setText(StrUtil.format("您的验证码：{}", vcode));
        mailSender.send(mimeMessage);
        stringRedisTemplate.opsForValue().set("vcode:" + email, vcode, 30, TimeUnit.MINUTES);
    }
}
