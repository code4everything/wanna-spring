package org.code4everything.springbee;

import org.code4everything.springbee.constant.TestConsts;
import org.code4everything.springbee.dao.UserDAO;
import org.code4everything.springbee.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pantao
 * @since 2018/10/25
 */
@SpringBootTest
public class BaseTest {

    @Autowired
    private UserDAO userDAO;

    protected User getUser() {
        return userDAO.getByEmail(TestConsts.EMAIL);
    }
}
