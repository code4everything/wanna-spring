package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.springbee.BaseTest;
import org.code4everything.springbee.domain.Category;
import org.code4everything.springbee.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceImplTest extends BaseTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void exists() {
        Assert.assertFalse(categoryService.exists(getUser().getId(), RandomUtil.randomString(6)));
    }

    @Test
    public void listCategory() {
        List<Category> categories = categoryService.listCategory(getUser().getId());
        Assert.assertNotNull(categories);
        System.out.println(categories);
    }

    @Test
    public void appendCategory() {
        Assert.assertNotNull(categoryService.appendCategory(getUser().getId(), RandomUtil.randomString(6)));
    }
}
