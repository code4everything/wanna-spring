package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Todo;

import java.sql.Date;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface TodoService {

    List<Todo> listTodo(Date doingDate);

    List<Date> listDate(String userId);

    Todo updateTodoStatus(String todoId, String status);

    Todo updateTodo(String todoId, String content);

    void remove(String todoId);

    Todo saveTodo(String userId, Date doingDate, String content);
}
