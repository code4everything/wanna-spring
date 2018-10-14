package org.code4everything.springbee.service.impl;

import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void updateInfo() {
    }

    @Test
    public void register() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("tao@zhahzapan.com");
        registerDTO.setPassword("123456");
        registerDTO.setUsername("pantao");
        userService.register(registerDTO);
    }

    @Test
    public void resetPassword() {
        userService.resetPassword("tao@zhazhapan.com", "123456");
    }

    @Test
    public void updatePassword() {
    }

    @Test
    public void login() {
    }
}