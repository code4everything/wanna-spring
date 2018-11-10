package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/asset")
@Api(value = "/user/asset")
public class AssetController extends BeeBaseController {

    private final IncomeService incomeService;

    @Autowired
    public AssetController(IncomeService incomeService, RedisTemplate<String, User> userRedisTemplate) {
        super(userRedisTemplate);
        this.incomeService = incomeService;
    }

    @GetMapping("/balance")
    @ApiOperation("获取流动资产")
    public ResponseResult<Long> getBalance() {
        return parseResult("未知错误", incomeService.getAssetBalance(getUserId()));
    }
}
