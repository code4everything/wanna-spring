package org.code4everything.springbee.web;

import io.swagger.annotations.*;
import org.code4everything.boot.message.VerifyCodeUtils;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.springbee.constant.BeeErrorConsts;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.PasswordVO;
import org.code4everything.springbee.model.RegisterVO;
import org.code4everything.springbee.model.UserInfoVO;
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

    @Autowired
    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @PatchMapping("/password/update")
    @ApiOperation("更新密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "oldPassword", required = true, value = "原密码"),
            @ApiImplicitParam(name = "newPassword", required = true, value = "新密码")})
    public Response<Boolean> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return parseBoolean("更新成功", "原密码不正确", userService.updatePassword(getUser(), oldPassword, newPassword));
    }

    @PatchMapping("/password/reset")
    @ApiOperation("重置密码")
    public Response resetPassword(@RequestBody @ApiParam @Valid PasswordVO passwordVO) {
        boolean correct = VerifyCodeUtils.validateAndRemove(passwordVO.getEmail(), passwordVO.getVcode());
        AssertUtils.throwIf(!correct, BeeErrorConsts.CODE_INCORRECT);
        userService.resetPassword(passwordVO.getEmail(), passwordVO.getNewPassword());
        return successResult("重置密码成功");
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Response register(@RequestBody @ApiParam @Valid RegisterVO registerVO) {
        boolean correct = VerifyCodeUtils.validateAndRemove(registerVO.getEmail(), registerVO.getVcode());
        AssertUtils.throwIf(!correct, BeeErrorConsts.CODE_INCORRECT);
        userService.register(registerVO);
        return successResult("注册成功");
    }

    @PostMapping("/login")
    @ApiOperation("登录，返回Token")
    @ApiImplicitParams({@ApiImplicitParam(name = "loginName", value = "用户名或邮箱", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)})
    public Response<String> login(@RequestParam String loginName, @RequestParam String password) {
        return successResult("登录成功", userService.login(loginName, password));
    }

    @PatchMapping("/info")
    @ApiOperation("更新信息")
    public Response updateInfo(@RequestBody @ApiParam @Valid UserInfoVO userInfoVO) {
        userService.updateInfo(getUser(), userInfoVO);
        return successResult("更新成功");
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Response<User> getUserInfo() {
        return successResult(getUser(), true);
    }
}
