package org.code4everything.springbee.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeDTO;
import org.code4everything.springbee.model.QueryIncomeDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/asset/income")
@Api(value = "/user/asset/income", description = "收益详情")
public class IncomeController {

    @PostMapping("/append")
    @ApiOperation("添加一条收益记录")
    public ResultObject<Income> saveIncome(@RequestBody @ApiParam IncomeDTO income) {
        return new ResultObject<>();
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条收益记录")
    @ApiImplicitParam(name = "incomeId", value = "收益记录编号", required = true)
    public ResultObject<Object> remove(@RequestParam String incomeId) {
        return new ResultObject<>();
    }

    @PutMapping("/{incomeId}/update")
    @ApiOperation("更新记录")
    public ResultObject<Object> updateIncome(@PathVariable String incomeId, @RequestBody @ApiParam IncomeDTO income) {
        return new ResultObject<>();
    }

    @GetMapping("/list")
    @ApiOperation("列出收益详情")
    public ResultObject<List<Income>> list(@RequestBody @ApiParam QueryIncomeDTO queryIncome) {
        return new ResultObject<>();
    }
}
