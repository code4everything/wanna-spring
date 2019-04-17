package org.code4everything.springbee.service.impl;

import org.code4everything.boot.annotation.AopLog;
import org.code4everything.springbee.dao.UserDAO;
import org.code4everything.springbee.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018/9/16
 */
@Service
public class CommonServiceImpl implements CommonService {

    private final UserDAO userDAO;

    @Autowired
    public CommonServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @AopLog("查看用户名是否存在")
    public boolean existsUsername(String username) {
        return userDAO.existsByUsername(username);
    }

    @Override
    @AopLog("查看邮箱是否存在")
    public boolean existsEmail(String email) {
        return userDAO.existsByEmail(email);
    }
}
