package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Job;
import org.code4everything.springbee.model.JobDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author pantao
 * @since 2019/3/1
 **/
public interface JobService {

    Job updateStatus(String id, String status);

    List<String> listCompany(String userId);

    Page<Job> listByWorkOverTime(String userId, String status, Integer offset, Integer size);

    Page<Job> listAll(String userId, Integer offset, Integer size);

    Page<Job> listByCompanies(String userId, String company, Integer offset, Integer size);

    Job writeWorkDiary(String id, String workDiary);

    Job finishedWork(String id, String workWay, String company);

    Job startWorking(String userId, String workWay, String company);

    Job save(String userId, JobDTO jobDTO);

    Job getJobOfToday(String userId);
}
