package org.code4everything.springbee.web;

import io.swagger.annotations.*;
import org.code4everything.boot.bean.Response;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.service.DailyService;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/10
 */
@RestController
@RequestMapping("/user/daily")
@Api(tags = "日程接口")
public class DailyController extends BeeBaseController {

    private final DailyService dailyService;

    @Autowired
    public DailyController(DailyService dailyService, UserService userService) {
        super(userService);
        this.dailyService = dailyService;
    }

    @PostMapping("/create")
    @ApiOperation("添加记录")
    public Response<Daily> saveDaily(@RequestBody @ApiParam @Valid DailyDTO daily) {
        ifReturn(daily.getDate().after(new Date(System.currentTimeMillis())), errorResult("添加失败，无法添加未来的日程记录"));
        ifReturn(() -> dailyService.exists(getUserId(), "", daily), errorResult("添加失败，该日期记录已经存在"));
        if (hasResult()) {
            return getReturn();
        }
        return parseResult("添加失败", dailyService.saveDaily(getUserId(), daily), true);
    }

    @GetMapping("/get")
    @ApiImplicitParam(name = "date", value = "日期", required = true, dataTypeClass = Date.class)
    public Response<Daily> getDaily(@RequestParam Date date) {
        return parseResult("该日期还没有记录哦", dailyService.getDaily(getUserId(), date), true);
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除记录")
    @ApiImplicitParam(name = "dailyId", value = "记录编号")
    public Response<String> removeDaily(@RequestParam String dailyId) {
        dailyService.remove(dailyId);
        return successResult("删除成功");
    }

    @PutMapping("/{dailyId}/update")
    @ApiOperation("更新记录")
    public Response<Daily> updateDaily(@PathVariable String dailyId, @RequestBody @ApiParam @Valid DailyDTO daily) {
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
    public Response<List<Daily>> listByDate(@RequestParam Date start, @RequestParam Date end) {
        return parseCollection("查询失败", dailyService.listDaily(getUserId(), start, end), true);
    }
}
