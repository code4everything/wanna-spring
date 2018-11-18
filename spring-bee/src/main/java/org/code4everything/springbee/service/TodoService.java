package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Todo;
import org.code4everything.springbee.model.TodoCountVO;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface TodoService {

    ArrayList<Todo> listUndoBeforeDate(String userId, String date);

    ArrayList<Todo> listTodo(String userId, String doingDate);

    ArrayList<String> listDate(String userId);

    ArrayList<TodoCountVO> listTodoCount(String userId, Date start, Date end);

    Todo updateTodoStatus(String todoId, String status);

    Todo updateTodo(String todoId, String content);

    void remove(String todoId);

    Todo saveTodo(String userId, String doingDate, String content);
}
