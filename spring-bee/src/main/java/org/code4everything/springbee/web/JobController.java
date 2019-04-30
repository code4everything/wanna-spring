package org.code4everything.springbee.web;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.base.bean.Response;
import org.code4everything.springbee.domain.Job;
import org.code4everything.springbee.model.JobVO;
import org.code4everything.springbee.service.JobService;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author pantao
 * @since 2019-03-01
 */
@RestController
@RequestMapping("/user/job")
@Api(tags = "工作日志接口")
public class JobController extends BeeBaseController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService, UserService userService) {
        super(userService);
        this.jobService = jobService;
    }

    @PutMapping("/{jobId}/status")
    @ApiOperation("更新加班状态")
    @ApiImplicitParam(name = "status", value = "状态:0未处理，1已处理", defaultValue = "1")
    public Response<Job> updateStatus(@PathVariable String jobId, @RequestParam(defaultValue = "1") String status) {
        return successResult(jobService.updateStatus(jobId, status));
    }

    @GetMapping("/companies")
    @ApiOperation("列出公司")
    public Response<Set<String>> listCompanies() {
        return successResult(jobService.listCompany(getUserId()));
    }

    @PutMapping("/{jobId}/diary")
    @ApiOperation("更新工作日记")
    public Response<Job> saveWorkDiary(@PathVariable String jobId, @RequestBody String workDiary) {
        return successResult(jobService.writeWorkDiary(jobId, workDiary));
    }

    @PutMapping("/{jobId}/finish")
    @ApiOperation("下班打卡")
    @ApiImplicitParams({@ApiImplicitParam(name = "workWay", value = "方式：1正常，2加班", defaultValue = "1"),
            @ApiImplicitParam(name = "company", value = "公司名称", required = true)})
    public Response<Job> finishWork(@PathVariable String jobId, @RequestParam(defaultValue = "1") String workWay,
                                    @RequestParam String company) {
        return successResult(jobService.finishedWork(jobId, workWay, company));
    }

    @PostMapping("/start")
    @ApiOperation("上班打卡")
    @ApiImplicitParams({@ApiImplicitParam(name = "workWay", value = "方式：1正常，2加班", defaultValue = "1"),
            @ApiImplicitParam(name = "company", value = "公司名称", required = true)})
    public Response<Job> startWork(@RequestParam(defaultValue = "1") String workWay, @RequestParam String company) {
        return successResult(jobService.startWorking(getUserId(), workWay, company));
    }

    @PutMapping("")
    @ApiOperation("保存工作日志")
    public Response<Job> save(@RequestBody JobVO jobVO) {
        return successResult(jobService.save(getUserId(), jobVO));
    }

    @GetMapping("/today")
    @ApiOperation("获取今日的工作日志")
    public Response<Job> getJobOfToday() {
        return parseResult("今日还没有工作日志哦", jobService.getJobOfToday(getUserId()));
    }

    @GetMapping("/overtime")
    @ApiImplicitParams({@ApiImplicitParam(name = "status", value = "状态:0未处理，1已处理", defaultValue = "0"),
            @ApiImplicitParam(name = "offset", value = "页偏移", defaultValue = "0"), @ApiImplicitParam(name = "size",
            value = "页大小", defaultValue = "30")})
    public Response<Page<Job>> listWorkOvertime(@RequestParam(defaultValue = "0") String status,
                                                @RequestParam(defaultValue = "0") Integer offset,
                                                @RequestParam(defaultValue = "30") Integer size) {
        return successResult(jobService.listByWorkOverTime(getUserId(), status, offset, size));
    }

    @GetMapping("")
    @ApiOperation("列出工作日志")
    @ApiImplicitParams({@ApiImplicitParam(name = "company", value = "公司名称"), @ApiImplicitParam(name = "offset",
            value = "页偏移", defaultValue = "0"), @ApiImplicitParam(name = "size", value = "页大小", defaultValue = "30")})
    public Response<Page<Job>> listByCompany(String company, @RequestParam(defaultValue = "0") Integer offset,
                                             @RequestParam(defaultValue = "30") Integer size) {
        if (StrUtil.isEmpty(company)) {
            return successResult(jobService.listAllWorked(getUserId(), offset, size));
        } else {
            return successResult(jobService.listWorkedByCompanies(getUserId(), company, offset, size));
        }
    }
}
