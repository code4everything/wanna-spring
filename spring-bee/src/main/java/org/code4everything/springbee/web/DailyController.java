package org.code4everything.springbee.web;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.model.DailyDateVO;
import org.code4everything.springbee.model.QueryDailyDTO;
import org.code4everything.springbee.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/10
 */
@RestController
@RequestMapping("/user/daily")
@Api(value = "/user/daily", description = "日程记录接口")
public class DailyController extends BeeBaseController {

    private final DailyService dailyService;

    @Autowired
    public DailyController(DailyService dailyService) {this.dailyService = dailyService;}

    @PostMapping("/create")
    @ApiOperation("添加记录")
    public ResultObject<Daily> saveDaily(@RequestBody @ApiParam DailyDTO daily) throws NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        CheckResult<Daily> result = Checker.checkBean(daily);
        if (result.passed) {
            return parseResult("添加失败", dailyService.saveDaily(getUserId(), daily));
        }
        return result.resultObject;
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除记录")
    @ApiImplicitParam(name = "dailyId", value = "记录编号")
    public ResultObject<Object> removeDaily(@RequestParam String dailyId) {
        return new ResultObject<>();
    }

    @PutMapping("/{dailyId}/update")
    @ApiOperation("更新记录")
    public ResultObject<Daily> updateDaily(@PathVariable String dailyId, @RequestBody @ApiParam DailyDTO daily) throws InvocationTargetException, IllegalAccessException {
        CheckResult<Daily> result = Checker.checkBean(daily);
        if (result.passed) {
            if (dailyService.exists(getUserId(), daily)) {
                return CheckResult.getErrorResult("更新失败，该日期记录已经存在");
            }
            return parseResult("更新失败", dailyService.updateDaily(dailyId, daily));
        }
        return result.resultObject;
    }

    @GetMapping("/list")
    @ApiOperation("列出日程记录")
    public ResultObject<List<Daily>> listByDate(@RequestBody @ApiParam QueryDailyDTO queryDaily) {
        return new ResultObject<>();
    }

    @GetMapping("/date/list")
    @ApiOperation("列出记录的日期")
    public ResultObject<List<DailyDateVO>> listDailyDate() {
        return new ResultObject<>();
    }
}
