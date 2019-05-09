package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.message.VerifyCodeUtils;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.springbee.constant.BeeErrorConsts;
import org.code4everything.springbee.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pantao
 * @since 2018/9/10
 */
@RestController
@RequestMapping("/common")
@Api(tags = "公共接口")
public class CommonController extends BeeBaseController {

    private final CommonService commonService;

    @Autowired
    public CommonController(CommonService commonService) {this.commonService = commonService;}

    @GetMapping("/username/exists")
    @ApiOperation("用户名是否存在")
    @ApiImplicitParam(name = "username", required = true, value = "用户名")
    public Response<Boolean> existsUsername(@RequestParam String username) {
        return parseBoolean("用户名已被注册啦", "用户名未被注册", commonService.existsUsername(username));
    }

    @GetMapping("/email/exists")
    @ApiOperation("邮箱是否存在")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true)
    public Response<Boolean> existsEmail(@RequestParam String email) {
        return parseBoolean("邮箱已被注册啦", "邮箱未被注册", commonService.existsEmail(email));
    }

    @GetMapping("/vcode/verify")
    @ApiOperation("验证码是否正确")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "邮箱", required = true), @ApiImplicitParam(name =
            "vcode", required = true, value = "验证码")})
    public Response<Boolean> verifyVcode(@RequestParam String email, @RequestParam String vcode) {
        return parseBoolean("验证通过", "验证码错误", VerifyCodeUtils.validate(email, vcode));
    }

    @PostMapping("/vcode/send")
    @ApiOperation("发送验证码")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true)
    public Response<String> sendVcode(@RequestParam String email) {
        AssertUtils.throwIf(VerifyCodeUtils.isFrequently(email), BeeErrorConsts.CODE_EXCEPTION);
        VerifyCodeUtils.sendByMailAsync(email, "验证码", "您的验证码：{}，请勿泄漏给他人");
        return successResult("发送成功");
    }

    @GetMapping("/current-time")
    @ApiOperation("获取服务器当前时间")
    public Response getCurrentTimestamp() {
        return successResult();
    }
}
