package org.code4everything.springbee.web;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.IncomeDTO;
import org.code4everything.springbee.model.QueryIncomeDTO;
import org.code4everything.springbee.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/asset/income")
@Api(value = "/user/asset/income", description = "收益详情")
public class IncomeController extends BeeBaseController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService, HttpServletRequest request,
                            RedisTemplate<String, User> userRedisTemplate) {
        super(request, userRedisTemplate);
        this.incomeService = incomeService;
    }

    @PostMapping("/append")
    @ApiOperation("添加一条收益记录")
    public ResultObject<Income> saveIncome(@RequestBody @ApiParam IncomeDTO income) throws IllegalAccessException,
            InstantiationException, InvocationTargetException {
        CheckResult<Income> result = Checker.checkBean(income);
        if (result.passed) {
            return parseResult("添加失败", incomeService.saveIncome(getUserId(), income));
        }
        return result.resultObject;
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条收益记录")
    @ApiImplicitParam(name = "incomeId", value = "收益记录编号", required = true)
    public ResultObject<Object> remove(@RequestParam String incomeId) {
        incomeService.remove(incomeId);
        return new ResultObject<>("删除成功");
    }

    @PutMapping("/{incomeId}/update")
    @ApiOperation("更新记录")
    public ResultObject<Income> updateIncome(@PathVariable String incomeId, @RequestBody @ApiParam IncomeDTO income) throws InvocationTargetException, IllegalAccessException {
        CheckResult<Income> result = Checker.checkBean(income);
        if (result.passed) {
            return parseResult("更新失败", incomeService.updateIncome(getUserId(), incomeId, income));
        }
        return new ResultObject<>();
    }

    @GetMapping("/list")
    @ApiOperation("列出收益详情")
    public ResultObject<List<Income>> list(@RequestBody @ApiParam QueryIncomeDTO queryIncome) {
        return parseResult("没有找到数据", incomeService.listIncome(getUserId(), queryIncome));
    }
}
