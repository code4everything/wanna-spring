package org.code4everything.springbee.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import org.code4everything.boot.annotations.AopLog;
import org.code4everything.springbee.dao.DailyDAO;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.service.DailyService;
import org.code4everything.springbee.util.BeeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

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
    @AopLog("查找日程记录")
    public Daily getDaily(String userId, Date date) {
        return dailyDAO.getByUserIdAndDate(userId, DateUtil.formatDate(date));
    }

    @Override
    @AopLog("列出日程记录")
    public List<Daily> listDaily(String userId, Date startDate, Date endDate) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId);
        BeeUtils.betweenStartAndEnd(criteria, startDate, endDate);
        query.addCriteria(criteria);
        query.with(new Sort(Sort.Direction.ASC, "date"));
        return mongoTemplate.find(query, Daily.class);
    }

    @Override
    @AopLog("删除日程记录")
    public void remove(String dailyId) {
        dailyDAO.deleteById(dailyId);
    }

    @Override
    @AopLog("添加日程记录")
    public Daily saveDaily(String userId, DailyDTO dailyDTO) {
        Daily daily = parseDailyDTO(dailyDTO, null);
        daily.setCreateTime(System.currentTimeMillis());
        daily.setId(IdUtil.simpleUUID());
        daily.setUserId(userId);
        return dailyDAO.save(daily);
    }

    @Override
    @AopLog("检测日程记录是否存在")
    public boolean exists(String userId, String dailyId, DailyDTO dailyDTO) {
        Daily daily = dailyDAO.getByUserIdAndDate(userId, DateUtil.formatDate(dailyDTO.getDate()));
        return ObjectUtil.isNotNull(daily) && !dailyId.equals(daily.getId());
    }

    @Override
    @AopLog("更新日程记录")
    public Daily updateDaily(String dailyId, DailyDTO dailyDTO) {
        Daily daily = dailyDAO.getById(dailyId);
        if (ObjectUtil.isNull(daily)) {
            return null;
        }
        return dailyDAO.save(parseDailyDTO(dailyDTO, daily));
    }

    private Daily parseDailyDTO(DailyDTO dailyDTO, Daily daily) {
        if (Objects.isNull(daily)) {
            daily = new Daily();
        }
        BeanUtils.copyProperties(dailyDTO, daily);
        if (Objects.isNull(dailyDTO.getDate())) {
            dailyDTO.setDate(new Date(System.currentTimeMillis()));
        }
        daily.setDate(DateUtil.formatDate(dailyDTO.getDate()));
        return daily;
    }
}
