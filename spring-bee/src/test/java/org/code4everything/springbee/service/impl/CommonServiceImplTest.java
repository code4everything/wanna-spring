package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.boot.message.VerifyCodeUtils;
import org.code4everything.springbee.constant.TestConsts;
import org.code4everything.springbee.service.CommonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonServiceImplTest {

    @Autowired
    private CommonService commonService;

    @Test
    public void sendVcode() throws MessagingException {
        VerifyCodeUtils.sendVerifyCodeByEmail(TestConsts.EMAIL, "验证码", "你的验证码：{}");
    }

    @Test
    public void existsUsername() {
        Assert.assertFalse(commonService.existsUsername(RandomUtil.randomString(16)));
    }

    @Test
    public void existsEmail() {
        Assert.assertFalse(commonService.existsEmail(RandomUtil.randomString(16)));
    }

    @Test
    public void isVcodeValidated() {
        Assert.assertFalse(VerifyCodeUtils.validateVerifyCodeAndRemove(TestConsts.EMAIL, RandomUtil.randomString(6)));
    }
}
