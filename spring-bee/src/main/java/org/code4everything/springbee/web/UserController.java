package org.code4everything.springbee.web;

import io.swagger.annotations.*;
import org.code4everything.boot.base.bean.Response;
import org.code4everything.boot.message.VerifyCodeUtils;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.PasswordVO;
import org.code4everything.springbee.model.RegisterVO;
import org.code4everything.springbee.model.UserInfoVO;
import org.code4everything.springbee.service.CommonService;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pantao
 * @since 2018/9/10
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController extends BeeBaseController {

    private final UserService userService;

    private final CommonService commonService;

    @Autowired
    public UserController(UserService userService, CommonService commonService) {
        super(userService);
        this.userService = userService;
        this.commonService = commonService;
    }

    @PutMapping("/password/update")
    @ApiOperation("更新密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "oldPassword", required = true, value = "原密码"),
            @ApiImplicitParam(name = "newPassword", required = true, value = "新密码")})
    public Response<Boolean> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return parseResult("更新成功", "原密码不正确", userService.updatePassword(getUser(), oldPassword, newPassword), false);
    }

    @PutMapping("/password/reset")
    @ApiOperation("重置密码")
    public Response<String> resetPassword(@RequestBody @ApiParam @Valid PasswordVO passwordVO) {
        if (VerifyCodeUtils.validateAndRemove(passwordVO.getEmail(), passwordVO.getVcode())) {
            userService.resetPassword(passwordVO.getEmail(), passwordVO.getNewPassword());
            return successResult("重置密码成功");
        }
        return errorResult("验证码不正确");
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Response<String> register(@RequestBody @ApiParam @Valid RegisterVO register) {
        ifReturn(commonService.existsUsername(register.getUsername()), errorResult("该用户名已经被注册啦"));
        ifReturn(() -> commonService.existsEmail(register.getEmail()), errorResult("该邮箱已经注册啦"));
        if (hasResult()) {
            return getReturn();
        }
        if (VerifyCodeUtils.validateAndRemove(register.getEmail(), register.getVcode())) {
            userService.register(register);
            return successResult("注册成功");
        }
        return errorResult("验证码不正确");
    }

    @PutMapping("/login")
    @ApiOperation("登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "loginName", value = "用户名或邮箱", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)})
    public Response<String> login(@RequestParam String loginName, @RequestParam String password) {
        return parseResult("登录成功", "登录失败", userService.login(loginName, password));
    }

    @PutMapping("/info")
    @ApiOperation("更新信息")
    public Response<Boolean> updateInfo(@RequestBody @ApiParam @Valid UserInfoVO userInfo) {
        return parseResult("更新", userService.updateInfo(getUser(), userInfo));
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Response<User> getUserInfo() {
        return parseResult("未获取到用户信息，请登录", getUser(), true);
    }
}
