package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.web.mvc.BaseSignController;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/asset")
@Api(tags = "资产接口")
public class AssetController extends BaseSignController<User> {

    private final IncomeService incomeService;

    @Autowired
    public AssetController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/balance")
    @ApiOperation("获取流动资产")
    public Response<Long> getBalance() {
        return successResult(incomeService.getAssetBalance(getUser().getId()));
    }
}
