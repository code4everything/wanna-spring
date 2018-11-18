package org.code4everything.springbee.web;

import io.swagger.annotations.*;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.IncomeBillVO;
import org.code4everything.springbee.model.IncomeDTO;
import org.code4everything.springbee.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/asset/income")
@Api(value = "/user/asset/income")
public class IncomeController extends BeeBaseController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService, RedisTemplate<String, User> userRedisTemplate) {
        super(userRedisTemplate);
        this.incomeService = incomeService;
    }

    @PostMapping("/append")
    @ApiOperation("添加一条收益记录")
    public ResponseResult<Income> saveIncome(@RequestBody @ApiParam @Valid IncomeDTO income) {
        return parseResult("添加失败", incomeService.saveIncome(getUserId(), income), true);
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条收益记录")
    @ApiImplicitParam(name = "incomeId", value = "收益记录编号", required = true)
    public ResponseResult<String> remove(@RequestParam String incomeId) {
        incomeService.remove(getUserId(), incomeId);
        return successResult("删除成功");
    }

    @PutMapping("/{incomeId}/update")
    @ApiOperation("更新记录")
    public ResponseResult<Income> updateIncome(@PathVariable String incomeId,
                                               @RequestBody @ApiParam @Valid IncomeDTO income) {
        return parseResult("更新失败", incomeService.updateIncome(getUserId(), incomeId, income), true);
    }

    @GetMapping("/list")
    @ApiOperation("列出收益详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "category", value = "分类"), @ApiImplicitParam(name = "start", value =
            "开始日期"), @ApiImplicitParam(name = "end", value = "截止日期")})
    public ResponseResult<ArrayList<Income>> list(String category, Date start, Date end) {
        return parseResult("没有找到数据", incomeService.listIncome(getUserId(), category, start, end), true);
    }

    @GetMapping("/month/list")
    @ApiOperation("列出月账单")
    @ApiImplicitParams({@ApiImplicitParam(name = "startMonth", value = "开始月份", required = true, example = "2018-01"),
            @ApiImplicitParam(name = "endMonth", value = "结束月份", required = true, example = "2018-12")})
    public ResponseResult<ArrayList<IncomeBillVO>> listMonth(@RequestParam String startMonth,
                                                             @RequestParam String endMonth) {
        return parseResult("糟糕，没有数据", incomeService.listMonth(getUserId(), startMonth, endMonth));
    }

    @GetMapping("/year/list")
    @ApiOperation("列出年账单")
    @ApiImplicitParams({@ApiImplicitParam(name = "startYear", value = "开始年份", required = true, example = "2014",
            dataType = "int"), @ApiImplicitParam(name = "endYear", value = "结束年份", required = true, example = "2018",
            dataType = "int")})
    public ResponseResult<ArrayList<IncomeBillVO>> listYear(@RequestParam Integer startYear,
                                                            @RequestParam Integer endYear) {
        return parseResult("糟糕，没有数据", incomeService.listYear(getUserId(), startYear, endYear));
    }
}
