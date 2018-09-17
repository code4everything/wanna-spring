package org.code4everything.springbee.service.impl;

import org.code4everything.springbee.service.CommonService;
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
        commonService.sendVcode("735817834@qq.com");
    }
}