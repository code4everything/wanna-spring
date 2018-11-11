package org.code4everything.springbee.web;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.*;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public DailyController(DailyService dailyService, RedisTemplate<String, User> userRedisTemplate) {
        super(userRedisTemplate);
        this.dailyService = dailyService;
    }

    @PostMapping("/create")
    @ApiOperation("添加记录")
    public ResponseResult<Daily> saveDaily(@RequestBody @ApiParam @Valid DailyDTO daily) {
        if (daily.getDate().getTime() > DateUtil.endOfDay(new java.util.Date()).getTime()) {
            return errorResult("添加失败，无法添加未来的日程记录");
        }
        if (dailyService.exists(getUserId(), "", daily)) {
            return errorResult("添加失败，该日期记录已经存在");
        }
        return parseResult("添加失败", dailyService.saveDaily(getUserId(), daily), true);
    }

    @GetMapping("/get")
    @ApiImplicitParam(name = "date", value = "日期", required = true, dataTypeClass = Date.class)
    public ResponseResult<Daily> getDaily(@RequestParam Date date) {
        return parseResult("该日期还没有记录哦", dailyService.getDaily(getUserId(), date), true);
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除记录")
    @ApiImplicitParam(name = "dailyId", value = "记录编号")
    public ResponseResult<String> removeDaily(@RequestParam String dailyId) {
        dailyService.remove(dailyId);
        return successResult("删除成功");
    }

    @PutMapping("/{dailyId}/update")
    @ApiOperation("更新记录")
    public ResponseResult<Daily> updateDaily(@PathVariable String dailyId,
                                             @RequestBody @ApiParam @Valid DailyDTO daily) {
        if (dailyService.exists(getUserId(), dailyId, daily)) {
            return errorResult("更新失败，该日期记录已经存在");
        }
        return parseResult("更新失败", dailyService.updateDaily(dailyId, daily), true);
    }

    @GetMapping("/list")
    @ApiOperation("列出日程记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "start", value = "开始时间", required = true,
            dataTypeClass = Date.class), @ApiImplicitParam(name = "end", value = "结束时间", required = true,
            dataTypeClass = Date.class)})
    public ResponseResult<ArrayList<Daily>> listByDate(@RequestParam Date start, @RequestParam Date end) {
        return parseResult("查询失败", dailyService.listDaily(getUserId(), start, end), true);
    }
}
