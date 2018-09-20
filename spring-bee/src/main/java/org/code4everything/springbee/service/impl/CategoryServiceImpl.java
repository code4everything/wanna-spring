package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.annotation.AopLog;
import org.code4everything.springbee.dao.CategoryDAO;
import org.code4everything.springbee.domain.Category;
import org.code4everything.springbee.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/9/20
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoryServiceImpl(CategoryDAO categoryDAO) {this.categoryDAO = categoryDAO;}

    @Override
    @AopLog("检查分类是否存在")
    public boolean exists(String userId, String name) {
        return categoryDAO.existsByUserIdAndName(userId, name);
    }

    @Override
    @AopLog("添加收益分类")
    public Category appendCategory(String userId, String name) {
        Category category = new Category();
        category.setCreateTime(new Timestamp(System.currentTimeMillis()));
        category.setId(RandomUtil.simpleUUID());
        category.setName(name);
        category.setUserId(userId);
        return categoryDAO.save(category);
    }
}
