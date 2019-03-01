package org.code4everything.springbee.web;

import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.UserService;

/**
 * @author pantao
 * @since 2018/9/19
 */
public class BeeBaseController extends BaseController {

    private UserService userService;

    public BeeBaseController() {}

    public BeeBaseController(UserService userService) {
        this.userService = userService;
    }

    protected String getUserId() {
        return getUser().getId();
    }

    protected User getUser() {
        return getUser(userService);
    }
}
