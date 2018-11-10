package org.code4everything.springbee.web;

import io.swagger.annotations.*;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.PasswordDTO;
import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.model.UserInfoDTO;
import org.code4everything.springbee.service.CommonService;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author pantao
 * @since 2018/9/10
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user")
public class UserController extends BeeBaseController {

    private final UserService userService;

    private final CommonService commonService;

    @Autowired
    public UserController(UserService userService, CommonService commonService,
                          RedisTemplate<String, User> userRedisTemplate) {
        super(userRedisTemplate);
        this.userService = userService;
        this.commonService = commonService;
    }

    @PutMapping("/password/update")
    @ApiOperation("更新密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "oldPassword", required = true, value = "原密码"),
            @ApiImplicitParam(name = "newPassword", required = true, value = "新密码")})
    public ResponseResult<Boolean> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return parseResult("更新成功", "原密码不正确", userService.updatePassword(getUser(), oldPassword, newPassword));
    }

    @PutMapping("/password/reset")
    @ApiOperation("重置密码")
    public ResponseResult<String> resetPassword(@RequestBody @ApiParam PasswordDTO password) {
        if (commonService.isVcodeValidated(password.getEmail(), password.getVcode(), true)) {
            userService.resetPassword(password.getEmail(), password.getNewPassword());
            return new ResponseResult<String>().setMsg("重置密码成功");
        }
        return new ResponseResult<String>().setMsg("验证码不正确");
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public ResponseResult<String> register(@RequestBody @ApiParam RegisterDTO register) {
        if (commonService.existsUsername(register.getUsername())) {
            return new ResponseResult<String>().setMsg("该用户名已经被注册啦");
        }
        if (commonService.existsEmail(register.getEmail())) {
            return new ResponseResult<String>().setMsg("该邮箱已经注册啦");
        }
        if (commonService.isVcodeValidated(register.getEmail(), register.getVcode(), true)) {
            userService.register(register);
            return new ResponseResult<String>().setMsg("注册成功");
        }
        return new ResponseResult<String>().setMsg("验证码不正确");
    }

    @PutMapping("/login")
    @ApiOperation("登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "loginName", value = "用户名或邮箱", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)})
    public ResponseResult<String> login(@RequestParam String loginName, @RequestParam String password) {
        return parseResult("登录成功", "登录失败", userService.login(loginName, password));
    }

    @PutMapping("/info")
    @ApiOperation("更新信息")
    public ResponseResult<Boolean> updateInfo(@RequestBody @ApiParam UserInfoDTO userInfo) {
        return parseResult("更新", userService.updateInfo(getUser(), userInfo));
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public ResponseResult<User> getUserInfo() {
        return parseResult("未获取到用户信息，请登录", getUser());
    }
}
