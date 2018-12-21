package org.code4everything.springbee.web;

import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.UserService;

import java.util.Objects;

/**
 * @author pantao
 * @since 2018/9/19
 */
public class BeeBaseController extends BaseController {

    private UserService userService;

    private User user = null;

    public BeeBaseController() {}

    public BeeBaseController(UserService userService) {
        this.userService = userService;
    }

    protected String getUserId() {
        return getUser().getId();
    }

    protected User getUser() {
        if (Objects.isNull(user)) {
            user = getUser(userService);
        }
        return user;
    }
}
