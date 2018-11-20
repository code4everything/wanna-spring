package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import org.code4everything.boot.annotations.AopLog;
import org.code4everything.springbee.dao.CategoryDAO;
import org.code4everything.springbee.domain.Category;
import org.code4everything.springbee.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

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
    public void updateCategory(String categoryId, String name) {
        Optional<Category> categoryOptional = categoryDAO.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            if (!name.equals(category.getName())) {
                category.setName(name);
                categoryDAO.save(category);
            }
        }
    }

    @Override
    public void removeCategory(String categoryId) {
        categoryDAO.deleteById(categoryId);
    }

    @Override
    @AopLog("检查分类是否存在")
    public boolean exists(String userId, String name) {
        return categoryDAO.existsByUserIdAndName(userId, name);
    }

    @Override
    @AopLog("列出分类")
    public ArrayList<Category> listCategory(String userId) {
        return categoryDAO.getByUserId(userId);
    }

    @Override
    @AopLog("添加收益分类")
    public Category appendCategory(String userId, String name) {
        Category category = new Category();
        category.setCreateTime(System.currentTimeMillis());
        category.setId(IdUtil.simpleUUID());
        category.setName(name);
        category.setUserId(userId);
        return categoryDAO.save(category);
    }
}
