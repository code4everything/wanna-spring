package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import org.code4everything.springbee.dao.JobDAO;
import org.code4everything.springbee.domain.Job;
import org.code4everything.springbee.model.JobDTO;
import org.code4everything.springbee.service.JobService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author pantao
 * @since 2019/3/1
 **/
@Service
public class JobServiceImpl implements JobService {

    private final JobDAO jobDAO;

    @Autowired
    public JobServiceImpl(JobDAO jobDAO) {this.jobDAO = jobDAO;}

    @Override
    public Job save(String userId, JobDTO jobDTO) {
        Job job;
        Optional<Job> jobOptional = jobDAO.findById(jobDTO.getId());
        if (jobOptional.isPresent()) {
            job = jobOptional.get();
            BeanUtils.copyProperties(jobDTO, job);
        } else {
            job = jobDTO.copyInto(new Job());
            job.setId(IdUtil.simpleUUID());
            job.setCreateTime(System.currentTimeMillis());
            job.setUserId(userId);
        }
        return jobDAO.save(job);
    }
}
