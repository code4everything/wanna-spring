package org.code4everything.springbee.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.code4everything.boot.base.DateUtils;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.springbee.constant.BeeErrorConsts;
import org.code4everything.springbee.dao.JobDAO;
import org.code4everything.springbee.domain.Job;
import org.code4everything.springbee.model.JobVO;
import org.code4everything.springbee.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2019/3/1
 */
@Service
public class JobServiceImpl implements JobService {

    private static final String COMPANY_KEY_PREFIX = "company.";

    private final JobDAO jobDAO;

    private final RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    public JobServiceImpl(JobDAO jobDAO, @Qualifier("stringRedis") RedisTemplate<String, String> stringRedisTemplate) {
        this.jobDAO = jobDAO;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    @LogMethod("更新状态")
    public Job updateStatus(String id, String status) {
        Optional<Job> jobOptional = jobDAO.findById(id);
        AssertUtils.throwIf(jobOptional, BeeErrorConsts.JOB_NOT_FOUND);
        Job job = jobOptional.get();
        job.setStatus(status);
        return jobDAO.save(job);
    }

    @Override
    @LogMethod("列出公司名称")
    public Set<String> listCompany(String userId) {
        final String key = COMPANY_KEY_PREFIX + userId;
        List<String> companiesFormRedis = stringRedisTemplate.opsForList().range(key, 0, 128);
        if (CollUtil.isEmpty(companiesFormRedis)) {
            List<Job> jobs = jobDAO.getByUserId(userId);
            final Set<String> companies = new HashSet<>(jobs.size());
            jobs.forEach(job -> companies.add(job.getCompany()));

            if (CollUtil.isNotEmpty(companies)) {
                stringRedisTemplate.opsForList().leftPushAll(key, companies);
                expireCompanyAfterThreeDays(key);
            }
            return companies;
        }
        return new HashSet<>(companiesFormRedis);
    }

    @Override
    @LogMethod("列出加班的工作记录")
    public Page<Job> listByWorkOverTime(String userId, String status, Integer offset, Integer size) {
        return jobDAO.getByUserIdAndWorkWayAndStatus(userId, "2", status, getPageable(offset, size));
    }

    @Override
    @LogMethod("列出所有工作记录")
    public Page<Job> listAllWorked(String userId, Integer offset, Integer size) {
        return jobDAO.getByUserIdAndWorkWay(userId, "1", getPageable(offset, size));
    }

    @Override
    @LogMethod("列出公司的所有工作记录")
    public Page<Job> listWorkedByCompanies(String userId, String company, Integer offset, Integer size) {
        return jobDAO.getByUserIdAndCompanyAndWorkWay(userId, company, "1", getPageable(offset, size));
    }

    @Override
    @LogMethod("保存工作日志")
    public Job writeWorkDiary(String id, String workDiary) {
        Optional<Job> jobOptional = jobDAO.findById(id);
        AssertUtils.throwIf(jobOptional, BeeErrorConsts.JOB_NOT_FOUND);
        Job job = jobOptional.get();
        job.setWorkDiary(workDiary);
        return jobDAO.save(job);
    }

    @Override
    @LogMethod("下班打卡")
    public Job finishedWork(String id, String workWay, String company) {
        Optional<Job> jobOptional = jobDAO.findById(id);
        AssertUtils.throwIf(jobOptional, BeeErrorConsts.JOB_NOT_FOUND);
        Job job = jobOptional.get();
        job.setWorkWay(workWay);
        job.setWorkTimeEnd(System.currentTimeMillis());
        job.setCompany(company);
        job.setStatus("0");
        pushCompany(job.getUserId(), company);
        return jobDAO.save(job);
    }

    @Override
    @LogMethod("上班打卡")
    public Job startWorking(String userId, String workWay, String company) {
        AssertUtils.throwIfNotNull(getJobOfToday(userId), BeeErrorConsts.JOB_EXISTS);
        Job job = getNewJob(userId);
        job.setWorkTimeStart(System.currentTimeMillis());
        job.setWorkWay(workWay);
        job.setCompany(company);
        job.setWorkTimeEnd(System.currentTimeMillis());
        pushCompany(userId, company);
        return jobDAO.save(job);
    }

    @Override
    @LogMethod("新增工作记录")
    public Job save(String userId, JobVO jobVO) {
        Optional<Job> job = jobDAO.findById(jobVO.getId());
        pushCompany(userId, jobVO.getCompany());
        return jobDAO.save(jobVO.copyInto(job.orElseGet(() -> getNewJob(userId))));
    }

    @Override
    @LogMethod("获取今天的工作记录")
    public Job getJobOfToday(String userId) {
        return jobDAO.getByUserIdAndWorkTimeStartAfter(userId, DateUtils.getStartOfToday().getTime());
    }

    private void pushCompany(String userId, String company) {
        ThreadUtil.execute(() -> {
            final String key = COMPANY_KEY_PREFIX + userId;
            Set<String> companies = listCompany(userId);
            if (StrUtil.isNotEmpty(company) && (CollUtil.isEmpty(companies) || !companies.contains(company))) {
                stringRedisTemplate.opsForList().rightPush(key, company);
            }
            expireCompanyAfterThreeDays(key);
        });
    }

    private void expireCompanyAfterThreeDays(final String key) {
        stringRedisTemplate.expire(key, 3, TimeUnit.DAYS);
    }

    private Job getNewJob(String userId) {
        Job job = new Job();
        job.setUserId(userId);
        job.setId(IdUtil.simpleUUID());
        job.setCreateTime(System.currentTimeMillis());
        job.setStatus("0");
        return job;
    }

    private Pageable getPageable(Integer offset, Integer size) {
        return PageRequest.of(offset, size, new Sort(Sort.Direction.DESC, "workTimeStart"));
    }
}
