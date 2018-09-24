package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Todo;

import java.sql.Date;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface TodoService {

    Todo saveTodo(String userId, Date doingDate, String content);
}
