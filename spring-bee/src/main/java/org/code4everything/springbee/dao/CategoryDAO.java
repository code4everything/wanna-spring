package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/20
 */
@Repository
public interface CategoryDAO extends MongoRepository<Category, String> {

    /**
     * 验证分类是否存在
     *
     * @param userId 用户编号
     * @param name 用户名
     *
     * @return 分类是否存在
     */
    boolean existsByUserIdAndName(String userId, String name);

    /**
     * 列出用户的所有分类
     *
     * @param userId 用户编号
     *
     * @return 分类列表
     */
    List<Category> getByUserId(String userId);
}
