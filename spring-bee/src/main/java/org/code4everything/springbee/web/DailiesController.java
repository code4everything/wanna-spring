package org.code4everything.springbee.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/daily/detail")
@Api(value = "/user/daily/detail", description = "日程详情接口")
public class DailiesController {

    @PostMapping("/append/{dailyId}")
    @ApiOperation("添加一条详情记录")
    public ResultObject<Dailies> append(@PathVariable String dailyId, @RequestBody @ApiParam DailiesDTO dailies) {
        return new ResultObject<>();
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条详情")
    public ResultObject<Object> remove(@RequestParam String dailiesId) {
        return new ResultObject<>();
    }

    @PutMapping("/{dailiesId}/update")
    @ApiOperation("更新详情")
    public ResultObject<Dailies> updateDailies(@PathVariable String dailiesId,
                                               @RequestBody @ApiParam DailiesDTO dailies) {
        return new ResultObject<>();
    }

    @GetMapping("/list")
    @ApiOperation("列出日程详情")
    public ResultObject<List<Dailies>> listByDailyId(@RequestParam String dailyId) {
        return new ResultObject<>();
    }
}
