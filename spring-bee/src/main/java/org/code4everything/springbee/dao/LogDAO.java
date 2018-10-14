package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/10/14
 **/
@Repository
public interface LogDAO extends MongoRepository<Log, String> {}
