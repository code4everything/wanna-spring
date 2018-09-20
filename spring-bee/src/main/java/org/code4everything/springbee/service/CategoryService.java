package org.code4everything.springbee.service;

import com.zhazhapan.util.annotation.AopLog;
import org.code4everything.springbee.domain.Category;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/20
 */
public interface CategoryService {

    boolean exists(String userId, String name);

    List<Category> listCategory(String userId);

    @AopLog("添加收益分类")
    Category appendCategory(String userId, String name);
}
