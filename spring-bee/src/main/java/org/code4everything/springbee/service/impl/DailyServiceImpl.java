package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.zhazhapan.util.BeanUtils;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.annotation.AopLog;
import com.zhazhapan.util.model.SimpleDateTime;
import org.bson.Document;
import org.code4everything.springbee.dao.DailyDAO;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.model.DailyDateVO;
import org.code4everything.springbee.model.DailyMonthVO;
import org.code4everything.springbee.model.QueryDailyDTO;
import org.code4everything.springbee.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/22
 */
@Service
public class DailyServiceImpl implements DailyService {

    private final DailyDAO dailyDAO;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public DailyServiceImpl(DailyDAO dailyDAO, MongoTemplate mongoTemplate) {
        this.dailyDAO = dailyDAO;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @AopLog("检测日程记录是否存在")
    public boolean exists(String dailyId) {
        return dailyDAO.existsById(dailyId);
    }

    @Override
    @AopLog("列出记录的所有日期")
    public List<DailyDateVO> listDailyDate(String userId) {
        // TODO: 2018/9/23 由于此函数查询比较耗时，需将结果放入缓存中
        // 查询年份
        BasicDBObject dbObject = new BasicDBObject("userId", userId);
        MongoCollection<Document> collection = mongoTemplate.getCollection("daily");
        DistinctIterable<Integer> years = collection.distinct("year", dbObject, Integer.class);
        MongoCursor<Integer> yearCursor = years.iterator();
        List<DailyDateVO> dateVOList = new ArrayList<>(64);
        // 遍历年份
        while (yearCursor.hasNext()) {
            DailyDateVO dateVO = new DailyDateVO();
            Integer year = yearCursor.next();
            dateVO.setYear(year);
            // 查询月份
            dbObject.put("year", year);
            DistinctIterable<Integer> months = collection.distinct("month", dbObject, Integer.class);
            MongoCursor<Integer> monthCursor = months.iterator();
            List<DailyMonthVO> monthVOList = new ArrayList<>(16);
            // 遍历月份
            while (monthCursor.hasNext()) {
                DailyMonthVO monthVO = new DailyMonthVO();
                Integer month = monthCursor.next();
                monthVO.setMonth(month);
                // 查询日期
                dbObject.put("month", month);
                DistinctIterable<Integer> days = collection.distinct("day", dbObject, Integer.class);
                List<Integer> dayList = new ArrayList<>(32);
                for (Integer day : days) {
                    dayList.add(day);
                }
                monthVO.setDays(dayList);
                monthVOList.add(monthVO);
            }
            dateVO.setMonths(monthVOList);
            dateVOList.add(dateVO);
        }
        return dateVOList;
    }

    @Override
    @AopLog("列出日程记录")
    public List<Daily> listDaily(String userId, QueryDailyDTO query) {
        boolean queryMonth = query.getMonth() > 0;
        boolean queryDay = query.getDay() > 0;
        if (queryMonth && queryDay) {
            return dailyDAO.getByUserIdAndYearAndMonthAndDay(userId, query.getYear(), query.getMonth(), query.getDay());
        } else if (queryMonth) {
            return dailyDAO.getByUserIdAndYearAndMonth(userId, query.getYear(), query.getMonth());
        } else if (queryDay) {
            return dailyDAO.getByUserIdAndYearAndDay(userId, query.getYear(), query.getDay());
        } else {
            return dailyDAO.getByUserIdAndYear(userId, query.getYear());
        }
    }

    @Override
    @AopLog("删除日程记录")
    public void remove(String dailyId) {
        dailyDAO.deleteById(dailyId);
    }

    @Override
    @AopLog("添加日程记录")
    public Daily saveDaily(String userId, DailyDTO dailyDTO) throws InvocationTargetException, InstantiationException
            , IllegalAccessException {
        Daily daily = parseDailyDTO(dailyDTO);
        daily.setCreateTime(System.currentTimeMillis());
        daily.setId(RandomUtil.simpleUUID());
        daily.setUserId(userId);
        return dailyDAO.save(daily);
    }

    @Override
    @AopLog("检测日程记录是否存在")
    public boolean exists(String userId, DailyDTO dailyDTO) {
        SimpleDateTime date = new SimpleDateTime(dailyDTO.getDate());
        return dailyDAO.existsByUserIdAndYearAndMonthAndDay(userId, date.getYear(), date.getMonth(), date.getDay());
    }

    @Override
    @AopLog("更新日程记录")
    public Daily updateDaily(String dailyId, DailyDTO dailyDTO) throws InvocationTargetException,
            IllegalAccessException {
        Daily daily = dailyDAO.getById(dailyId);
        if (Checker.isNull(daily)) {
            return null;
        }
        return dailyDAO.save(parseDailyDTO(dailyDTO, BeanUtils.bean2Another(dailyDTO, daily)));
    }

    private Daily parseDailyDTO(DailyDTO dailyDTO) throws InvocationTargetException, InstantiationException,
            IllegalAccessException {
        return parseDailyDTO(dailyDTO, BeanUtils.bean2Another(dailyDTO, Daily.class));
    }

    private Daily parseDailyDTO(DailyDTO dailyDTO, Daily daily) throws InvocationTargetException,
            IllegalAccessException {
        return BeanUtils.bean2Another(new SimpleDateTime(dailyDTO.getDate()), daily);
    }
}
