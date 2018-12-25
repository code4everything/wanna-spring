package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Todo;
import org.code4everything.springbee.model.TodoCountVO;

import java.util.Date;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface TodoService {

    /**
     * 列出指定日期之前未完成的待办事项
     *
     * @param userId 用户编号
     * @param date 日期
     *
     * @return 待办事项列表
     */
    List<Todo> listUndoBeforeDate(String userId, Date date);

    /**
     * 列出所有代办事项
     *
     * @param userId 用户编号
     * @param doingDate 日期
     *
     * @return 待办事项列表
     */
    List<Todo> listTodo(String userId, Date doingDate);

    /**
     * 列出所有日期
     *
     * @param userId 用户编号
     *
     * @return 日期列表
     */
    List<String> listDate(String userId);

    /**
     * 统计每天的代办事项数量
     *
     * @param userId 用户编号
     * @param start 开始日期
     * @param end 截止日期
     *
     * @return 数量列表
     */
    List<TodoCountVO> listTodoCount(String userId, Date start, Date end);

    /**
     * 更新代办事项状态
     *
     * @param todoId 事项编号
     * @param status 状态：0未完成，1已完成
     *
     * @return 代办事项
     */
    Todo updateTodoStatus(String todoId, String status);

    /**
     * 更新待办事项内容
     *
     * @param todoId 事项编号
     * @param content 内容
     *
     * @return 代办事项
     */
    Todo updateTodo(String todoId, String content);

    /**
     * 删除事项
     *
     * @param todoId 事项编号
     */
    void remove(String todoId);

    /**
     * 保存代办事项
     *
     * @param userId 用户编号
     * @param doingDate 日期
     * @param content 内容
     *
     * @return 待办事项
     */
    Todo saveTodo(String userId, String doingDate, String content);
}
