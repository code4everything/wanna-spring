package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Job;
import org.code4everything.springbee.model.JobVO;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * @author pantao
 * @since 2019/3/1
 */
public interface JobService {

    Job updateStatus(String id, String status);

    Set<String> listCompany(String userId);

    Page<Job> listByWorkOverTime(String userId, String status, Integer offset, Integer size);

    Page<Job> listAllWorked(String userId, Integer offset, Integer size);

    Page<Job> listWorkedByCompanies(String userId, String company, Integer offset, Integer size);

    Job writeWorkDiary(String id, String workDiary);

    Job finishedWork(String id, String workWay, String company);

    Job startWorking(String userId, String workWay, String company);

    Job save(String userId, JobVO jobVO);

    Job getJobOfToday(String userId);
}
