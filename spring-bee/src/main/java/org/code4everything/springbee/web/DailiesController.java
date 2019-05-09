package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesVO;
import org.code4everything.springbee.service.DailiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/11
 */
@RestController
@RequestMapping("/user/daily/detail")
@Api(tags = "日程详细记录接口")
public class DailiesController extends BeeBaseController {

    private final DailiesService dailiesService;

    @Autowired
    public DailiesController(DailiesService dailiesService) {
        this.dailiesService = dailiesService;
    }

    @PostMapping("/append/{dailyId}")
    @ApiOperation("添加一条详情记录")
    public Response<Dailies> append(@PathVariable String dailyId, @RequestBody @ApiParam @Valid DailiesVO dailies) {
        return successResult(dailiesService.saveDailies(dailyId, dailies));
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条详情")
    @ApiImplicitParam(name = "dailiesId", value = "详情编号", required = true)
    public Response<String> remove(@RequestParam String dailiesId) {
        dailiesService.remove(dailiesId);
        return successResult("删除成功");
    }

    @PutMapping("/{dailiesId}/update")
    @ApiOperation("更新详情")
    public Response<Dailies> updateDailies(@PathVariable String dailiesId,
                                           @RequestBody @ApiParam @Valid DailiesVO dailies) {
        return successResult(dailiesService.updateDailies(dailiesId, dailies));
    }

    @GetMapping("/list")
    @ApiOperation("列出日程详情")
    @ApiImplicitParam(name = "dailyId", value = "日程记录编号", required = true)
    public Response<List<Dailies>> listByDailyId(@RequestParam String dailyId) {
        return parseCollection("没有找到相关记录哦", dailiesService.listDailies(dailyId));
    }
}
