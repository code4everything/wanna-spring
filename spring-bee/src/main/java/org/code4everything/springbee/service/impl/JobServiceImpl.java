package org.code4everything.springbee.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import org.code4everything.springbee.dao.JobDAO;
import org.code4everything.springbee.domain.Job;
import org.code4everything.springbee.exception.JobNotFoundException;
import org.code4everything.springbee.model.JobDTO;
import org.code4everything.springbee.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2019/3/1
 **/
@Service
public class JobServiceImpl implements JobService {

    private static final String COMPANY_KEY_PREFIX = "company.";

    private final JobDAO jobDAO;

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public JobServiceImpl(JobDAO jobDAO, StringRedisTemplate stringRedisTemplate) {
        this.jobDAO = jobDAO;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Job updateStatus(String id, String status) {
        Optional<Job> jobOptional = jobDAO.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setStatus(status);
            return jobDAO.save(job);
        }
        throw new JobNotFoundException();
    }

    @Override
    public List<String> listCompany(String userId) {
        final String key = COMPANY_KEY_PREFIX + userId;
        List<String> companies = stringRedisTemplate.opsForList().range(key, 0, 128);
        if (CollUtil.isEmpty(companies)) {
            List<Job> jobs = jobDAO.getDistinctByCompany();
            final List<String> temp = new ArrayList<>();
            jobs.forEach(job -> {
                if (job.getUserId().equals(userId)) {
                    temp.add(job.getCompany());
                }
            });
            companies = temp;
            stringRedisTemplate.opsForList().leftPushAll(key, companies);
        }
        expiredCompanyAfterThreeDays(key);
        return companies;
    }

    @Override
    public Page<Job> listByWorkOverTime(String userId, String status, Integer offset, Integer size) {
        return jobDAO.getByUserIdAndWorkWayAndStatus(userId, "2", status, getPageable(offset, size));
    }

    @Override
    public Page<Job> listAll(String userId, Integer offset, Integer size) {
        return jobDAO.getByUserId(userId, getPageable(offset, size));
    }

    @Override
    public Page<Job> listByCompany(String userId, String company, Integer offset, Integer size) {
        return jobDAO.getByUserIdAndCompany(userId, company, getPageable(offset, size));
    }

    @Override
    public Job writeWorkDiary(String id, String workDiary) {
        Optional<Job> jobOptional = jobDAO.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setWorkDiary(workDiary);
            return jobDAO.save(job);
        }
        throw new JobNotFoundException();
    }

    @Override
    public Job finishedWork(String id, String workWay, String company) {
        Optional<Job> jobOptional = jobDAO.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setWorkWay(workWay);
            job.setWorkTimeEnd(System.currentTimeMillis());
            job.setCompany(company);
            pushCompany(job.getUserId(), company);
            return jobDAO.save(job);
        }
        throw new JobNotFoundException();
    }

    @Override
    public Job startWorking(String userId, String workWay, String company) {
        Job job = getNewJob(userId);
        job.setWorkTimeStart(System.currentTimeMillis());
        job.setWorkWay(workWay);
        job.setCompany(company);
        pushCompany(userId, company);
        return jobDAO.save(job);
    }

    @Override
    public Job save(String userId, JobDTO jobDTO) {
        Optional<Job> job = jobDAO.findById(jobDTO.getId());
        pushCompany(userId, jobDTO.getCompany());
        return jobDAO.save(jobDTO.copyInto(job.orElseGet(() -> getNewJob(userId))));
    }

    public void pushCompany(String userId, String company) {
        ThreadUtil.execute(() -> {
            final String key = COMPANY_KEY_PREFIX + userId;
            List<String> companies = listCompany(userId);
            if (CollUtil.isEmpty(companies) || !companies.contains(company)) {
                stringRedisTemplate.opsForList().leftPush(key, company);
            }
            expiredCompanyAfterThreeDays(key);
        });
    }

    private void expiredCompanyAfterThreeDays(final String key) {
        stringRedisTemplate.expire(key, 3, TimeUnit.DAYS);
    }

    private Job getNewJob(String userId) {
        Job job = new Job();
        job.setUserId(userId);
        job.setId(IdUtil.simpleUUID());
        job.setCreateTime(System.currentTimeMillis());
        return job;
    }

    private Pageable getPageable(Integer offset, Integer size) {
        return PageRequest.of(offset, size, new Sort(Sort.Direction.DESC, "workTimeStart"));
    }
}
