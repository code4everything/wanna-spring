package org.code4everything.springbee.web;

import io.swagger.annotations.*;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeDTO;
import org.code4everything.springbee.model.QueryIncomeDTO;
import org.code4everything.springbee.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
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
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("/append")
    @ApiOperation("添加一条收益记录")
    public ResponseResult<Income> saveIncome(@RequestBody @ApiParam IncomeDTO income) throws IllegalAccessException,
            InstantiationException, InvocationTargetException {
        return parseResult("添加失败", incomeService.saveIncome(getUserId(), income));
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条收益记录")
    @ApiImplicitParam(name = "incomeId", value = "收益记录编号", required = true)
    public ResponseResult<String> remove(@RequestParam String incomeId) {
        incomeService.remove(getUserId(), incomeId);
        return new ResponseResult<String>().setMsg("删除成功");
    }

    @PutMapping("/{incomeId}/update")
    @ApiOperation("更新记录")
    public ResponseResult<Income> updateIncome(@PathVariable String incomeId,
                                               @RequestBody @ApiParam IncomeDTO income) throws InvocationTargetException, IllegalAccessException {
        return parseResult("更新失败", incomeService.updateIncome(getUserId(), incomeId, income));
    }

    @GetMapping("/list")
    @ApiOperation("列出收益详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "category", value = "分类"), @ApiImplicitParam(name = "start", value =
            "开始日期"), @ApiImplicitParam(name = "end", value = "截止日期")})
    public ResponseResult<ArrayList<Income>> list(String category, String start, String end) {
        QueryIncomeDTO queryIncome = new QueryIncomeDTO(start, end, category);
        return parseResult("没有找到数据", incomeService.listIncome(getUserId(), queryIncome));
    }
}
