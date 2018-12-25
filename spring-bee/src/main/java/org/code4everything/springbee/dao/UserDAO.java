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

    /**
     * 通过邮箱获取用户
     *
     * @param mail 邮箱
     *
     * @return 用户
     */
    User getByEmail(String mail);

    /**
     * 邮箱是否存在
     *
     * @param mail 邮箱
     *
     * @return 是否存在
     */
    boolean existsByEmail(String mail);

    /**
     * 用户名是否存在
     *
     * @param username 用户名
     *
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 通过用户名或邮箱查找用户
     *
     * @param username 用户名
     * @param mail 邮箱
     *
     * @return 用户
     */
    User getByUsernameOrEmail(String username, String mail);
}
