package org.code4everything.springbee.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.code4everything.boot.base.constant.IntegerConsts;
import org.code4everything.boot.base.constant.StringConsts;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.springbee.dao.TodoDAO;
import org.code4everything.springbee.domain.Todo;
import org.code4everything.springbee.model.TodoCountVO;
import org.code4everything.springbee.model.TodoVO;
import org.code4everything.springbee.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/24
 */
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoDAO todoDAO;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TodoServiceImpl(TodoDAO todoDAO, MongoTemplate mongoTemplate) {
        this.todoDAO = todoDAO;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @LogMethod("获取指定日期之前所有未完成的待办事项")
    public List<Todo> listUndoBeforeDate(String userId, Date date) {
        return todoDAO.getByUserIdAndStatusAndDoingDateLessThan(userId, "0", DateUtil.formatDate(date));
    }

    @Override
    @LogMethod("列出指定日期的待办事项")
    public List<Todo> listTodo(String userId, Date doingDate) {
        return todoDAO.getByUserIdAndDoingDate(userId, DateUtil.formatDate(doingDate));
    }

    @Override
    @LogMethod("列出所有待办事项日期")
    public List<String> listDate(String userId) {
        BasicDBObject dbObject = new BasicDBObject("userId", userId);
        MongoCollection<Document> collection = mongoTemplate.getCollection("todo");
        DistinctIterable<String> iterable = collection.distinct("doingDate", dbObject, String.class);
        List<String> dateList = new ArrayList<>(128);
        for (String date : iterable) {
            dateList.add(date);
        }
        return dateList;
    }

    @Override
    public List<TodoCountVO> listTodoCount(String userId, Date start, Date end) {
        List<TodoCountVO> list = new ArrayList<>();
        end = DateUtil.endOfDay(end);
        while (start.before(end)) {
            TodoCountVO countVO = new TodoCountVO();
            String startDate = DateUtil.formatDate(start);
            countVO.setDate(startDate);
            countVO.setScore(todoDAO.countByUserIdAndDoingDate(userId, startDate));
            list.add(countVO);
            start = DateUtil.offsetDay(start, 1);
        }
        return list;
    }

    @Override
    @LogMethod("更新待办事项状态")
    public Todo updateTodoStatus(String todoId, String status) {
        Todo todo = todoDAO.getById(todoId);
        if (ObjectUtil.isNull(todo)) {
            return null;
        }
        if (StringConsts.Sign.ONE.equals(status)) {
            todo.setStatus("1");
            todo.setDoneTime(System.currentTimeMillis());
        } else {
            todo.setStatus("0");
        }
        return todoDAO.save(todo);
    }

    @Override
    @LogMethod("更新待办事项内容")
    public Todo updateTodo(String todoId, String content) {
        Todo todo = todoDAO.getById(todoId);
        if (ObjectUtil.isNull(todo)) {
            return null;
        }
        // 状态已完成时不能修改内容
        if (StringConsts.Sign.ONE.equals(todo.getStatus())) {
            return todo;
        }
        todo.setContent(content);
        return todoDAO.save(todo);
    }

    @Override
    @LogMethod("删除待办事项")
    public void remove(String todoId) {
        todoDAO.deleteById(todoId);
    }

    @Override
    @LogMethod("添加待办事项")
    public Todo saveTodo(String userId, TodoVO todoVO) {
        Todo todo = new Todo();
        todo.setContent(todoVO.getContent());
        todo.setCreateTime(System.currentTimeMillis());
        todo.setDoingDate(todoVO.getDoingDate());
        todo.setId(IdUtil.simpleUUID());
        todo.setStatus("0");
        todo.setUserId(userId);
        todoDAO.save(todo);
        if (todoVO.getOffsetWell() > 0 && todoVO.getRepeatWell() > 0) {
            // 异步批量添加
            ThreadUtil.execute(() -> {
                long offset = 0;
                final long nowMilli = DateUtil.parseDate(todoVO.getDoingDate()).getTime();
                final long offsetMillis = (long) todoVO.getOffset() * (long) IntegerConsts.ONE_DAY_MILLIS;
                for (int i = 0; i < todoVO.getRepeat(); i++) {
                    offset += offsetMillis;
                    Todo another = todo.copyInto(new Todo());
                    another.setCreateTime(System.currentTimeMillis());
                    another.setDoingDate(DateUtil.formatDate(new Date(nowMilli + offset)));
                    another.setId(IdUtil.simpleUUID());
                    another.setUserId(userId);
                    todoDAO.save(another);
                }
            });
        }
        return todo;
    }
}
