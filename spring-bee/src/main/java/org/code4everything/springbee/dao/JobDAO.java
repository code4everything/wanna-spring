package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2019/3/1
 **/
@Repository
public interface JobDAO extends MongoRepository<Job, String> {


}
