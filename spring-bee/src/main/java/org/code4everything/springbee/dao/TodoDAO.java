package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/24
 */
@Repository
public interface TodoDAO extends MongoRepository<Todo, String> {

    /**
     * 列出指定日期之前的代办事项
     *
     * @param userId 用户编号
     * @param status 状态
     * @param doingDate 截止日期
     *
     * @return 待办事项列表
     */
    ArrayList<Todo> getByUserIdAndStatusAndDoingDateLessThan(String userId, String status, String doingDate);

    /**
     * 获取代办事项信息
     *
     * @param todoId 事项编号
     *
     * @return 代办事项
     */
    Todo getById(String todoId);

    /**
     * 统计指定日期的代办事项数量
     *
     * @param userId 用户编号
     * @param doingDate 日期
     *
     * @return 待办事项的数量
     */
    Integer countByUserIdAndDoingDate(String userId, String doingDate);

    /**
     * 列出某个日期的所有代办事项
     *
     * @param userId 用户编号
     * @param doingDate 日期
     *
     * @return 代办事项列表
     */
    ArrayList<Todo> getByUserIdAndDoingDate(String userId, String doingDate);
}
