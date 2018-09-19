package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/9/15
 */
@Repository
public interface UserDAO extends MongoRepository<User, String> {

    User getByEmail(String mail);

    Integer countByEmail(String mail);

    Integer countByUsername(String username);

    User getByUsernameOrEmail(String username, String mail);
}
