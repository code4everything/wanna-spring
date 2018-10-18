package org.code4everything.springbee.web;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.*;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.PasswordDTO;
import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.model.UserInfoDTO;
import org.code4everything.springbee.service.CommonService;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/9/10
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user", description = "用户接口")
public class UserController extends BeeBaseController {

    private final UserService userService;

    private final CommonService commonService;

    @Autowired
    public UserController(UserService userService, CommonService commonService, HttpServletRequest request,
                          RedisTemplate<String, User> userRedisTemplate) {
        super(request, userRedisTemplate, true);
        this.userService = userService;
        this.commonService = commonService;
    }

    @PutMapping("/password/update")
    @ApiOperation("更新密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "oldPassword", required = true, value = "原密码"),
            @ApiImplicitParam(name = "newPassword", required = true, value = "新密码")})
    public ResultObject updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return parseResult("更新成功", "原密码不正确", userService.updatePassword(getUser(), oldPassword, newPassword));
    }

    @PutMapping("/password/reset")
    @ApiOperation("重置密码")
    public ResultObject resetPassword(@RequestBody @ApiParam PasswordDTO password) {
        CheckResult result = Checker.checkBean(password);
        if (result.passed) {
            if (commonService.isVcodeValidated(password.getEmail(), password.getVcode(), true)) {
                userService.resetPassword(password.getEmail(), password.getNewPassword());
                return new ResultObject("重置密码成功");
            }
            return CheckResult.getErrorResult("验证码不正确");
        }
        return result.resultObject;
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
            if (commonService.isVcodeValidated(register.getEmail(), register.getVcode(), true)) {
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
    public ResultObject<String> login(@RequestParam String loginName, @RequestParam String password) {
        return parseResult("登录成功", "登录失败", userService.login(loginName, password), false);
    }

    @PutMapping("/info")
    @ApiOperation("更新信息")
    public ResultObject updateInfo(@RequestBody @ApiParam UserInfoDTO userInfo) {
        return parseResult("更新", userService.updateInfo(getUser(), userInfo));
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public ResultObject<User> getUserInfo() {
        return parseResult("未获取到用户信息，请登录", getUser());
    }
}
