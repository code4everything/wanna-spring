package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Job;
import org.code4everything.springbee.model.JobDTO;

/**
 * @author pantao
 * @since 2019/3/1
 **/
public interface JobService {

    Job save(String userId, JobDTO jobDTO);
}
