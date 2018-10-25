package org.code4everything.springbee.web;

import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018/10/25
 **/
@RestController
@RequestMapping("/error")
@Api(value = "/error", description = "错误接口")
public class ErrorController {

    @RequestMapping("/auth")
    @ApiOperation("身份验证失败")
    public ResultObject authError() {
        return CheckResult.getErrorResult(401, "身份验证失败，请登录");
    }

    @RequestMapping("/banned")
    @ApiOperation("禁止访问")
    public ResultObject bannedError() {
        return CheckResult.getErrorResult(400, "处于安全因素，该路径禁止访问");
    }
}
