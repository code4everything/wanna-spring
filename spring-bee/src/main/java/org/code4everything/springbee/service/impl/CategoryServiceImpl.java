package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.springbee.constant.BeeErrorConsts;
import org.code4everything.springbee.dao.CategoryDAO;
import org.code4everything.springbee.domain.Category;
import org.code4everything.springbee.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void updateCategory(String userId, String categoryId, String name) {
        // 如果分类名称不存在时，才更新分类名称
        if (!exists(userId, name)) {
            Optional<Category> categoryOptional = categoryDAO.findById(categoryId);
            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
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
    @LogMethod("检查分类是否存在")
    public boolean exists(String userId, String name) {
        return categoryDAO.existsByUserIdAndName(userId, name);
    }

    @Override
    @LogMethod("列出分类")
    public List<Category> listCategory(String userId) {
        return categoryDAO.getByUserId(userId);
    }

    @Override
    @LogMethod("添加收益分类")
    public Category appendCategory(String userId, String name) {
        AssertUtils.throwIf(exists(userId, name), BeeErrorConsts.CATEGORY_EXISTS);
        Category category = new Category();
        category.setCreateTime(System.currentTimeMillis());
        category.setId(IdUtil.simpleUUID());
        category.setName(name);
        category.setUserId(userId);
        return categoryDAO.save(category);
    }
}
