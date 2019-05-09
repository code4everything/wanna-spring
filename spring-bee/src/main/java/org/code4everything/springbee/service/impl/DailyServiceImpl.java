package org.code4everything.springbee.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.springbee.constant.BeeErrorConsts;
import org.code4everything.springbee.dao.DailyDAO;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyVO;
import org.code4everything.springbee.service.DailyService;
import org.code4everything.springbee.util.BeeUtils;
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
    @LogMethod("检测日程记录是否存在")
    public boolean exists(String dailyId) {
        return dailyDAO.existsById(dailyId);
    }

    @Override
    @LogMethod("查找日程记录")
    public Daily getDaily(String userId, Date date) {
        return dailyDAO.getByUserIdAndDate(userId, DateUtil.formatDate(date));
    }

    @Override
    @LogMethod("列出日程记录")
    public List<Daily> listDaily(String userId, Date startDate, Date endDate) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId);
        BeeUtils.betweenStartAndEnd(criteria, startDate, endDate);
        query.addCriteria(criteria);
        query.with(new Sort(Sort.Direction.ASC, "date"));
        return mongoTemplate.find(query, Daily.class);
    }

    @Override
    @LogMethod("删除日程记录")
    public void remove(String dailyId) {
        dailyDAO.deleteById(dailyId);
    }

    @Override
    @LogMethod("添加日程记录")
    public Daily saveDaily(String userId, DailyVO dailyVO) {
        AssertUtils.throwIf(exists(userId, "", dailyVO.getDate()), BeeErrorConsts.DAILY_EXISTS);
        Daily daily = new Daily().copyFrom(dailyVO);
        daily.setCreateTime(System.currentTimeMillis());
        daily.setId(IdUtil.simpleUUID());
        daily.setUserId(userId);
        return dailyDAO.save(daily);
    }

    @Override
    @LogMethod("检测日程记录是否存在")
    public boolean exists(String userId, String dailyId, Date date) {
        if (Objects.isNull(date)) {
            date = new Date(System.currentTimeMillis());
        }
        Daily daily = dailyDAO.getByUserIdAndDate(userId, DateUtil.formatDate(date));
        return ObjectUtil.isNotNull(daily) && !dailyId.equals(daily.getId());
    }

    @Override
    @LogMethod("更新日程记录")
    public Daily updateDaily(String userId, String dailyId, DailyVO dailyVO) {
        Daily daily = dailyDAO.getById(dailyId);
        AssertUtils.throwIfNull(daily, BeeErrorConsts.DAILY_NOT_FOUND);
        AssertUtils.throwIf(exists(userId, dailyId, dailyVO.getDate()), BeeErrorConsts.DAILY_EXISTS);
        return dailyDAO.save(daily.copyFrom(dailyVO));
    }
}
