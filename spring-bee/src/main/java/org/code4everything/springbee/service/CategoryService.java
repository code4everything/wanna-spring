package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Category;

import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/20
 */
public interface CategoryService {

    /**
     * 更新分类
     *
     * @param categoryId 分类的编号
     * @param name 分类名称
     */
    void updateCategory(String categoryId, String name);

    /**
     * 删除分类
     *
     * @param categoryId 分类的编号
     */
    void removeCategory(String categoryId);

    /**
     * 检测分类是否存在
     *
     * @param userId 用户名
     * @param name 分类名
     *
     * @return 是否存在
     */
    boolean exists(String userId, String name);

    /**
     * 列出用户所有分类
     *
     * @param userId 用户编号
     *
     * @return 分类列表
     */
    ArrayList<Category> listCategory(String userId);

    /**
     * 添加分类
     *
     * @param userId 用户编号
     * @param name 分类名称
     *
     * @return 分类
     */
    Category appendCategory(String userId, String name);
}
