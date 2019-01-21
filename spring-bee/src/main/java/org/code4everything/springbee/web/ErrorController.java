package org.code4everything.springbee.web;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.bean.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author pantao
 * @since 2018/10/25
 **/
@RestController
@RequestMapping("/error")
@Api(tags = "错误接口", hidden = true)
public class ErrorController extends BeeBaseController {

    @ApiIgnore
    @RequestMapping("/auth")
    @ApiOperation("身份验证失败")
    public Response<String> authError() {
        return errorResult(HttpStatus.HTTP_UNAUTHORIZED, "身份验证失败，请登录");
    }

    @ApiIgnore
    @RequestMapping("/banned")
    @ApiOperation("禁止访问")
    public Response<String> bannedError() {
        return errorResult(HttpStatus.HTTP_FORBIDDEN, "处于安全因素，该路径禁止访问");
    }
}
