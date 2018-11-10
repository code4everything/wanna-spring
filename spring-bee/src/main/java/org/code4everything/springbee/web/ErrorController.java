package org.code4everything.springbee.web;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.bean.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018/10/25
 **/
@RestController
@RequestMapping("/error")
@Api(value = "/error")
public class ErrorController {

    @RequestMapping("/auth")
    @ApiOperation("身份验证失败")
    public ResponseResult<String> authError() {
        return new ResponseResult<String>().error(HttpStatus.HTTP_UNAUTHORIZED, "身份验证失败，请登录");
    }

    @RequestMapping("/banned")
    @ApiOperation("禁止访问")
    public ResponseResult<String> bannedError() {
        return new ResponseResult<String>().error(HttpStatus.HTTP_FORBIDDEN, "处于安全因素，该路径禁止访问");
    }
}
