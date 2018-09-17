package org.code4everything.springbee.web;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.*;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.model.UserInfoDTO;
import org.code4everything.springbee.service.CommonService;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pantao
 * @since 2018/9/10
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user", description = "用户接口")
public class UserController {

    private final UserService userService;

    private final CommonService commonService;

    @Autowired
    public UserController(UserService userService, CommonService commonService) {
        this.userService = userService;
        this.commonService = commonService;
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public ResultObject register(@RequestBody @ApiParam RegisterDTO register) {
        CheckResult result = Checker.checkBean(register);
        if (result.passed) {
            if (commonService.existsUsername(register.getUsername())) {
                return CheckResult.getErrorResult("该用户名已经被注册啦");
            }
            if (commonService.existsEmail(register.getEmail())) {
                return CheckResult.getErrorResult("该邮箱已经注册啦");
            }
            if (commonService.isVcodeValidated(register.getEmail(), register.getVcode())) {
                userService.register(register);
                return new ResultObject("注册成功");
            }
            return CheckResult.getErrorResult("验证码不正确");
        }
        return result.resultObject;
    }

    @PutMapping("/login")
    @ApiOperation("登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "loginName", value = "用户名或邮箱", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)})
    public ResultObject<User> login(@RequestParam String loginName, @RequestParam String password) {
        return new ResultObject<>();
    }

    @PutMapping("/info")
    @ApiOperation("更新信息")
    public ResultObject<Object> updateInfo(@RequestBody @ApiParam UserInfoDTO userInfo) {
        return new ResultObject<>();
    }
}
