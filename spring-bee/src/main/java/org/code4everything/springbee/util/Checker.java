package org.code4everything.springbee.util;

import org.code4everything.boot.message.VerifyCodeUtils;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.springbee.constant.BeeErrorConsts;
import org.code4everything.springbee.service.CommonService;

/**
 * @author pantao
 * @since 2019-05-09
 */
public class Checker {

    private Checker() {}

    public static void checkUsername(CommonService commonService, String username) {
        AssertUtils.throwIf(commonService.existsUsername(username), BeeErrorConsts.USERNAME_EXISTS);
    }

    public static void checkEmail(CommonService commonService, String email) {
        AssertUtils.throwIf(commonService.existsEmail(email), BeeErrorConsts.EMAIL_EXISTS);
    }

    public static void checkVerifyCode(String email, String vcode) {
        VerifyCodeUtils.assertCorrect(email, vcode, true, BeeErrorConsts.CODE_INCORRECT);
    }
}
