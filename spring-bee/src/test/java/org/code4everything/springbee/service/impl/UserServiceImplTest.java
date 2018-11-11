package org.code4everything.springbee.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.code4everything.springbee.BaseTest;
import org.code4everything.springbee.constant.TestConsts;
import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.model.UserInfoDTO;
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
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setBio(TestConsts.BIO);
        userInfoDTO.setGender(TestConsts.GENDER);
        userInfoDTO.setNickname(TestConsts.NICKNAME);
        assert userService.updateInfo(getUser(), userInfoDTO);
    }

    /**
     * must run this method at first, unless the value is already exists
     */
    @Test
    public void register() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail(TestConsts.EMAIL);
        registerDTO.setPassword(TestConsts.PASSWORD);
        registerDTO.setUsername(TestConsts.USERNAME);
        userService.register(registerDTO);
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
