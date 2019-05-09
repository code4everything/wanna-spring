package org.code4everything.springbee.web;

import io.swagger.annotations.*;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyVO;
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
    public Response<Daily> saveDaily(@RequestBody @ApiParam @Valid DailyVO daily) {
        return successResult(dailyService.saveDaily(getUserId(), daily), true);
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
    public Response<Daily> updateDaily(@PathVariable String dailyId, @RequestBody @ApiParam @Valid DailyVO daily) {
        return successResult(dailyService.updateDaily(getUserId(), dailyId, daily), true);
    }

    @GetMapping("/list")
    @ApiOperation("列出日程记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "start", value = "开始时间", required = true,
            dataTypeClass = Date.class), @ApiImplicitParam(name = "end", value = "结束时间", required = true,
            dataTypeClass = Date.class)})
    public Response<List<Daily>> listByDate(@RequestParam Date start, @RequestParam Date end) {
        return parseCollection("该时间段还没有日程记录哦", dailyService.listDaily(getUserId(), start, end), true);
    }
}
