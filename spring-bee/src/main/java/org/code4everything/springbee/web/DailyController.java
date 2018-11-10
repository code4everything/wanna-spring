package org.code4everything.springbee.web;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.model.DailyDateVO;
import org.code4everything.springbee.model.QueryDailyDTO;
import org.code4everything.springbee.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/10
 */
@RestController
@RequestMapping("/user/daily")
@Api(value = "/user/daily")
public class DailyController extends BeeBaseController {

    private final DailyService dailyService;

    @Autowired
    public DailyController(DailyService dailyService, HttpServletRequest request,
                           RedisTemplate<String, User> userRedisTemplate) {
        super(userRedisTemplate);
        this.dailyService = dailyService;
    }

    @PostMapping("/create")
    @ApiOperation("添加记录")
    public ResponseResult<Daily> saveDaily(@RequestBody @ApiParam DailyDTO daily) throws NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        if (daily.getDate().getTime() > DateUtil.endOfDay(new java.util.Date()).getTime()) {
            return new ResponseResult<Daily>().error("添加失败，无法添加未来的日程记录");
        }
        if (dailyService.exists(getUserId(), "", daily)) {
            return new ResponseResult<Daily>().error("添加失败，该日期记录已经存在");
        }
        return parseResult("添加失败", dailyService.saveDaily(getUserId(), daily));
    }

    @GetMapping("/get")
    @ApiImplicitParam(name = "date", value = "日期", required = true, dataTypeClass = Date.class)
    public ResponseResult<Daily> getDaily(@RequestParam Date date) {
        return parseResult("该日期还没有记录哦", dailyService.getDaily(getUserId(), date));
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除记录")
    @ApiImplicitParam(name = "dailyId", value = "记录编号")
    public ResponseResult<String> removeDaily(@RequestParam String dailyId) {
        dailyService.remove(dailyId);
        return new ResponseResult<String>().setMsg("删除成功");
    }

    @PutMapping("/{dailyId}/update")
    @ApiOperation("更新记录")
    public ResponseResult<Daily> updateDaily(@PathVariable String dailyId, @RequestBody @ApiParam DailyDTO daily) throws InvocationTargetException, IllegalAccessException {
        if (dailyService.exists(getUserId(), dailyId, daily)) {
            return new ResponseResult<Daily>().error("更新失败，该日期记录已经存在");
        }
        return parseResult("更新失败", dailyService.updateDaily(dailyId, daily));
    }

    @GetMapping("/list")
    @ApiOperation("列出日程记录")
    public ResponseResult<ArrayList<Daily>> listByDate(@RequestBody @ApiParam QueryDailyDTO queryDaily) {
        return parseResult("查询失败", dailyService.listDaily(getUserId(), queryDaily));
    }

    @GetMapping("/date/list")
    @ApiOperation("列出记录的日期")
    public ResponseResult<ArrayList<DailyDateVO>> listDailyDate() {
        return parseResult("没有找到相关数据", dailyService.listDailyDate(getUserId()));
    }
}
