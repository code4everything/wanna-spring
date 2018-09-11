package org.code4everything.springbee.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.*;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.model.DailyDateVO;
import org.code4everything.springbee.model.QueryDailyDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/10
 */
@RestController
@RequestMapping("/user/daily")
@Api(value = "/user/daily", description = "日程记录接口")
public class DailyController {

    @PostMapping("/create")
    @ApiOperation("添加记录")
    public ResultObject<Daily> saveDaily(@RequestBody @ApiParam DailyDTO daily) {
        return new ResultObject<>();
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "dailyId", value = "记录编号"), @ApiImplicitParam(name = "date", value =
            "记录的日期")})
    public ResultObject<Object> removeDaily(String dailyId, String date) {
        return new ResultObject<>();
    }

    @PutMapping("/{dailyId}/update")
    @ApiOperation("更新记录")
    public ResultObject<Daily> updateDaily(@PathVariable String dailyId, @RequestBody @ApiParam DailyDTO daily) {
        return new ResultObject<>();
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
