package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/24
 */
@Repository
public interface TodoDAO extends MongoRepository<Todo, String> {

    List<Todo> getByUserIdAndStatusAndDoingDateLessThan(String userId, String status, String doingDate);

    Todo getById(String todoId);

    List<Todo> getByDoingDate(String doingDate);
}
