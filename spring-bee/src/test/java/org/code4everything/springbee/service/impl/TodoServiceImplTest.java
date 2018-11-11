package org.code4everything.springbee.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.code4everything.springbee.BaseTest;
import org.code4everything.springbee.constant.TestConsts;
import org.code4everything.springbee.dao.TodoDAO;
import org.code4everything.springbee.domain.Todo;
import org.code4everything.springbee.service.TodoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TodoServiceImplTest extends BaseTest {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoDAO todoDAO;

    @Test
    public void listTodo() {
        List<Todo> todos = todoService.listTodo(DateUtil.formatDate(new Date()));
        Assert.assertNotNull(todos);
        System.out.println(todos);
    }

    @Test
    public void listDate() {
        List<String> dates = todoService.listDate(getUser().getId());
        Assert.assertNotNull(dates);
        System.out.println(dates);
    }

    @Test
    public void updateTodoStatus() {
        Assert.assertNotNull(todoService.updateTodoStatus(todoDAO.findAll().get(0).getId(), TestConsts.STATUS));
    }

    @Test
    public void updateTodo() {
        Assert.assertNotNull(todoService.updateTodo(todoDAO.findAll().get(0).getId(), RandomUtil.randomString(6)));
    }

    @Test
    public void remove() {
        todoService.remove(todoDAO.findAll().get(0).getId());
    }

    @Test
    public void saveTodo() {
        Assert.assertNotNull(todoService.saveTodo(getUser().getId(), DateUtil.formatDate(new Date()),
                RandomUtil.randomString(6)));
    }
}
