package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/9/24
 */
@Repository
public interface TodoDAO extends MongoRepository<Todo, String> {}
