package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Todo;

import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface TodoService {

    ArrayList<Todo> listNotDoneBeforeDate(String userId, String date);

    ArrayList<Todo> listTodo(String doingDate);

    ArrayList<String> listDate(String userId);

    Todo updateTodoStatus(String todoId, String status);

    Todo updateTodo(String todoId, String content);

    void remove(String todoId);

    Todo saveTodo(String userId, String doingDate, String content);
}
