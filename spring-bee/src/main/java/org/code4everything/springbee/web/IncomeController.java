package org.code4everything.springbee.web;

import io.swagger.annotations.*;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeBillVO;
import org.code4everything.springbee.model.IncomeVO;
import org.code4everything.springbee.service.IncomeService;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/asset/income")
@Api(tags = "收益接口")
public class IncomeController extends BeeBaseController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService, UserService userService) {
        super(userService);
        this.incomeService = incomeService;
    }

    @PostMapping("/append")
    @ApiOperation("添加一条收益记录")
    public Response<Income> saveIncome(@RequestBody @ApiParam @Valid IncomeVO income) {
        return successResult(incomeService.saveIncome(getUserId(), income), true);
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条收益记录")
    @ApiImplicitParam(name = "incomeId", value = "收益记录编号", required = true)
    public Response<String> remove(@RequestParam String incomeId) {
        incomeService.remove(getUserId(), incomeId);
        return successResult("删除成功");
    }

    @PutMapping("/{incomeId}/update")
    @ApiOperation("更新记录")
    public Response<Income> updateIncome(@PathVariable String incomeId, @RequestBody @ApiParam @Valid IncomeVO income) {
        return successResult(incomeService.updateIncome(getUserId(), incomeId, income), true);
    }

    @GetMapping("/list")
    @ApiOperation("列出收益详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "category", value = "分类"), @ApiImplicitParam(name = "start", value =
            "开始日期"), @ApiImplicitParam(name = "end", value = "截止日期")})
    public Response<List<Income>> list(String category, Date start, Date end) {
        return parseCollection("没有找到相关数据哦", incomeService.listIncome(getUserId(), category, start, end), true);
    }

    @GetMapping("/month/list")
    @ApiOperation("列出月账单")
    @ApiImplicitParams({@ApiImplicitParam(name = "startMonth", value = "开始月份", required = true, example = "2018-01"),
            @ApiImplicitParam(name = "endMonth", value = "结束月份", required = true, example = "2018-12")})
    public Response<List<IncomeBillVO>> listMonth(@RequestParam String startMonth, @RequestParam String endMonth) {
        return parseCollection("该时间段还没有账单哦", incomeService.listMonth(getUserId(), startMonth, endMonth));
    }

    @GetMapping("/year/list")
    @ApiOperation("列出年账单")
    @ApiImplicitParams({@ApiImplicitParam(name = "startYear", value = "开始年份", required = true, example = "2014",
            dataType = "int"), @ApiImplicitParam(name = "endYear", value = "结束年份", required = true, example = "2018",
            dataType = "int")})
    public Response<List<IncomeBillVO>> listYear(@RequestParam Integer startYear, @RequestParam Integer endYear) {
        return parseCollection("该时间段还没有账单哦", incomeService.listYear(getUserId(), startYear, endYear));
    }
}
