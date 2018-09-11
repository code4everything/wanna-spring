package org.code4everything.springbee.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/asset")
@Api(value = "/user/asset", description = "流动资产")
public class AssetController {

    @GetMapping("/balance")
    @ApiOperation("获取流动资产")
    public ResultObject<Long> getBalance() {
        return new ResultObject<>();
    }
}
