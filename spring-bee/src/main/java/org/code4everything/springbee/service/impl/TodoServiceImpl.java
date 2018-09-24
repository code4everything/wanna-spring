package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.DateUtils;
import com.zhazhapan.util.annotation.AopLog;
import org.code4everything.springbee.dao.TodoDAO;
import org.code4everything.springbee.domain.Todo;
import org.code4everything.springbee.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * @author pantao
 * @since 2018/9/24
 */
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoDAO todoDAO;

    @Autowired
    public TodoServiceImpl(TodoDAO todoDAO) {this.todoDAO = todoDAO;}

    @Override
    @AopLog("更新待办事项状态")
    public Todo updateTodoStatus(String todoId, String status) {
        Todo todo = todoDAO.getById(todoId);
        if (Checker.isNull(todo)) {
            return null;
        }
        if (ValueConsts.ONE_STR.equals(status)) {
            todo.setStatus("1");
            todo.setDoneTime(DateUtils.getCurrentTimestamp());
        } else {
            todo.setStatus("0");
        }
        return todoDAO.save(todo);
    }

    @Override
    @AopLog("更新待办事项内容")
    public Todo updateTodo(String todoId, String content) {
        Todo todo = todoDAO.getById(todoId);
        if (Checker.isNull(todo)) {
            return null;
        }
        todo.setContent(content);
        return todoDAO.save(todo);
    }

    @Override
    @AopLog("删除待办事项")
    public void remove(String todoId) {
        todoDAO.deleteById(todoId);
    }

    @Override
    @AopLog("添加待办事项")
    public Todo saveTodo(String userId, Date doingDate, String content) {
        Todo todo = new Todo();
        todo.setContent(content);
        todo.setCreateTime(DateUtils.getCurrentTimestamp());
        todo.setDoingDate(doingDate);
        todo.setId(RandomUtil.simpleUUID());
        todo.setStatus("0");
        todo.setUserId(userId);
        return todoDAO.save(todo);
    }
}
