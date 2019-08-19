package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2019/3/1
 */
@Repository
public interface JobDAO extends MongoRepository<Job, String> {

    Page<Job> getByUserIdAndCompanyAndWorkWay(String userId, String company, String workWay, Pageable pageable);

    Page<Job> getByUserIdAndWorkWay(String userId, String workWay, Pageable pageable);

    Page<Job> getByUserIdAndWorkWayAndStatus(String userId, String workWay, String status, Pageable pageable);

    List<Job> getByUserId(String userId);

    Job getByUserIdAndWorkTimeStartAfter(String userId, Long today);
}
