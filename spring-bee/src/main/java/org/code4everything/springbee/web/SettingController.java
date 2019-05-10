package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.springbee.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pantao
 * @since 2019/5/10
 **/
@RestController
@RequestMapping("/user/setting")
@Api(tags = "用户配置")
public class SettingController extends BeeBaseController {

    private final SettingService settingService;

    @Autowired
    public SettingController(SettingService settingService) {this.settingService = settingService;}

    @GetMapping("/daily/rule/evaluate")
    @ApiOperation("获取用户自定义日程评分规则")
    public Response<String> getDailyEvaluateRule() {
        return successResult("请求成功", settingService.getDailyEvaluateRule(getUserId()));
    }

    @PatchMapping("/daily/rule/evaluate")
    @ApiOperation("更新用户自定义日程评分规则")
    public Response updateDailyEvaluateRule(@RequestParam String rule) {
        settingService.updateDailyEvaluateRule(getUserId(), rule);
        return successResult();
    }
}
