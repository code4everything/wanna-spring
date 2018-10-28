package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Todo;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface TodoService {

    List<Todo> listNotDoneBeforeDate(String userId, String date);

    List<Todo> listTodo(String doingDate);

    List<String> listDate(String userId);

    Todo updateTodoStatus(String todoId, String status);

    Todo updateTodo(String todoId, String content);

    void remove(String todoId);

    Todo saveTodo(String userId, String doingDate, String content);
}
