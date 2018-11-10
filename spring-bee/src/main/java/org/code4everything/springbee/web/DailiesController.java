package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesDTO;
import org.code4everything.springbee.service.DailiesService;
import org.code4everything.springbee.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/daily/detail")
@Api(value = "/user/daily/detail")
public class DailiesController extends BeeBaseController {

    private final DailyService dailyService;

    private final DailiesService dailiesService;

    @Autowired
    public DailiesController(DailyService dailyService, DailiesService dailiesService) {
        this.dailyService = dailyService;
        this.dailiesService = dailiesService;
    }

    @PostMapping("/append/{dailyId}")
    @ApiOperation("添加一条详情记录")
    public ResponseResult<Dailies> append(@PathVariable String dailyId, @RequestBody @ApiParam DailiesDTO dailies) {
        if (dailyService.exists(dailyId)) {
            return parseResult("添加失败", dailiesService.saveDailies(dailyId, dailies));
        }
        return new ResponseResult<Dailies>().error("添加失败，该日程记录不存在");
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条详情")
    @ApiImplicitParam(name = "dailiesId", value = "详情编号", required = true)
    public ResponseResult<String> remove(@RequestParam String dailiesId) {
        dailiesService.remove(dailiesId);
        return new ResponseResult<String>().setMsg("删除成功");
    }

    @PutMapping("/{dailiesId}/update")
    @ApiOperation("更新详情")
    public ResponseResult<Dailies> updateDailies(@PathVariable String dailiesId,
                                                 @RequestBody @ApiParam DailiesDTO dailies) {
        return parseResult("更新失败", dailiesService.updateDailies(dailiesId, dailies));
    }

    @GetMapping("/list")
    @ApiOperation("列出日程详情")
    @ApiImplicitParam(name = "dailyId", value = "日程记录编号", required = true)
    public ResponseResult<ArrayList<Dailies>> listByDailyId(@RequestParam String dailyId) {
        return parseResult("没有找到相关记录", dailiesService.listDailies(dailyId));
    }
}
