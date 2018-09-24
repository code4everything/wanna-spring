package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Todo;

import java.sql.Date;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface TodoService {

    Todo updateTodoStatus(String todoId, String status);

    Todo updateTodo(String todoId, String content);

    void remove(String todoId);

    Todo saveTodo(String userId, Date doingDate, String content);
}
