package org.code4everything.springbee.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "/common", description = "公共接口")
public class CommonController {

    private final CommonService commonService;

    @Autowired
    public CommonController(CommonService commonService) {this.commonService = commonService;}

    @GetMapping("/username/exists")
    @ApiOperation("用户名是否存在")
    @ApiImplicitParam(name = "username", required = true, value = "用户名")
    public ResultObject<Boolean> existsUsername(@RequestParam String username) {
        return new ResultObject<>();
    }

    @GetMapping("/email/exists")
    @ApiOperation("邮箱是否存在")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true)
    public ResultObject<Boolean> existsEmail(@RequestParam String email) {
        return new ResultObject<>();
    }

    @GetMapping("/vcode/verify")
    @ApiOperation("验证码是否正确")
    @ApiImplicitParam(name = "vcode", required = true, value = "验证码")
    public ResultObject<Boolean> verifyVcode(@RequestParam String code) {
        return new ResultObject<>();
    }

    @PostMapping("/vcode/send")
    @ApiOperation("发送验证码")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true)
    public ResultObject sendVcode(@RequestParam String email) throws MessagingException {
        commonService.sendVcode(email);
        return new ResultObject<>();
    }
}
