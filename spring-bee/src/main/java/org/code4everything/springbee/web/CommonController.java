package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.springbee.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

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
    public ResponseResult<Boolean> existsUsername(@RequestParam String username) {
        return parseBoolean("用户名存在", "用户名不存在", commonService.existsUsername(username));
    }

    @GetMapping("/email/exists")
    @ApiOperation("邮箱是否存在")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true)
    public ResponseResult<Boolean> existsEmail(@RequestParam String email) {
        return parseBoolean("邮箱存在", "邮箱不存在", commonService.existsEmail(email));
    }

    @GetMapping("/vcode/verify")
    @ApiOperation("验证码是否正确")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "邮箱", required = true), @ApiImplicitParam(name =
            "vcode", required = true, value = "验证码")})
    public ResponseResult<Boolean> verifyVcode(@RequestParam String email, @RequestParam String vcode) {
        return parseBoolean("验证通过", "验证码错误", commonService.isVcodeValidated(email, vcode, false));
    }

    @PostMapping("/vcode/send")
    @ApiOperation("发送验证码")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true)
    public ResponseResult<String> sendVcode(@RequestParam String email) throws MessagingException {
        commonService.sendVcode(email);
        return successResult("发送成功");
    }

    @GetMapping("/current-time")
    @ApiOperation("获取服务器当前时间")
    public ResponseResult<String> getCurrentTimestamp() {
        return successResult("");
    }
}
