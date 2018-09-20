package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/9/20
 */
@Repository
public interface CategoryDAO extends MongoRepository<Category, String> {

    boolean existsByUserIdAndName(String userId, String name);
}
