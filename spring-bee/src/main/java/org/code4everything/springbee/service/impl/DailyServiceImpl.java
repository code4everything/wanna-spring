package org.code4everything.springbee.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.BeanUtils;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.DateUtils;
import com.zhazhapan.util.annotation.AopLog;
import org.code4everything.springbee.dao.DailyDAO;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.model.DateBO;
import org.code4everything.springbee.model.QueryDailyDTO;
import org.code4everything.springbee.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author pantao
 * @since 2018/9/22
 */
@Service
public class DailyServiceImpl implements DailyService {

    private final DailyDAO dailyDAO;

    @Autowired
    public DailyServiceImpl(DailyDAO dailyDAO) {this.dailyDAO = dailyDAO;}

    @Override
    @AopLog("列出日程记录")
    public List<Daily> listDaily(String userId, QueryDailyDTO query) {
        boolean queryMonth = query.getMonth() > 0;
        Map
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
        daily.setCreateTime(DateUtils.getCurrentTimestamp());
        daily.setId(RandomUtil.simpleUUID());
        daily.setUserId(userId);
        return dailyDAO.save(daily);
    }

    @Override
    @AopLog("检测日程记录是否存在")
    public boolean exists(String userId, DailyDTO dailyDTO) {
        DateBO date = parseDailyDate(dailyDTO);
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
        return BeanUtils.bean2Another(parseDailyDate(dailyDTO), daily);
    }

    private DateBO parseDailyDate(DailyDTO dailyDTO) {
        DateBO dateBO = new DateBO();
        dateBO.setYear(DateUtil.year(dailyDTO.getDate()));
        dateBO.setMonth(DateUtil.month(dailyDTO.getDate()));
        dateBO.setDay(DateUtil.dayOfMonth(dailyDTO.getDate()));
        return dateBO;
    }
}
