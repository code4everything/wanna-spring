package org.code4everything.springbee.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.code4everything.springbee.BaseTest;
import org.code4everything.springbee.constant.TestConsts;
import org.code4everything.springbee.model.RegisterVO;
import org.code4everything.springbee.model.UserInfoVO;
import org.code4everything.springbee.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * You must test method register before any others test
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void updateInfo() {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setBio(TestConsts.BIO);
        userInfoVO.setGender(TestConsts.GENDER);
        userInfoVO.setNickname(TestConsts.NICKNAME);
        userService.updateInfo(getUser(), userInfoVO);
    }

    /**
     * must run this method at first, unless the value is already exists
     */
    @Test
    public void register() {
        RegisterVO registerVO = new RegisterVO();
        registerVO.setEmail(TestConsts.EMAIL);
        registerVO.setPassword(TestConsts.PASSWORD);
        registerVO.setUsername(TestConsts.USERNAME);
        userService.register(registerVO);
    }

    @Test
    public void resetPassword() {
        userService.resetPassword(TestConsts.EMAIL, TestConsts.PASSWORD);
        Assert.assertEquals(DigestUtil.md5Hex(TestConsts.PASSWORD), getUser().getPassword());
    }

    @Test
    public void updatePassword() {
        assert userService.updatePassword(getUser(), TestConsts.PASSWORD, TestConsts.PASSWORD);
    }

    @Test
    public void login() {
        Assert.assertNotNull(userService.login(TestConsts.EMAIL, TestConsts.PASSWORD));
    }
}
