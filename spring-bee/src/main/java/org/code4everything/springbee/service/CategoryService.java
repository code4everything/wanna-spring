package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Category;

import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/20
 */
public interface CategoryService {

    void updateCategory(String categoryId, String name);

    void removeCategory(String categoryId);

    boolean exists(String userId, String name);

    ArrayList<Category> listCategory(String userId);

    Category appendCategory(String userId, String name);
}
