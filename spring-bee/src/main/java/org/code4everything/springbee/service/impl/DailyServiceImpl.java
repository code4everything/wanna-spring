package org.code4everything.springbee.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.BeanUtils;
import com.zhazhapan.util.DateUtils;
import com.zhazhapan.util.annotation.AopLog;
import org.code4everything.springbee.dao.DailyDAO;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

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
    @AopLog("添加日程记录")
    public Daily saveDaily(String userId, DailyDTO dailyDTO) throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        Daily daily = parseDailyDTO(dailyDTO);
        daily.setCreateTime(DateUtils.getCurrentTimestamp());
        daily.setId(RandomUtil.simpleUUID());
        daily.setUserId(userId);
        return dailyDAO.save(daily);
    }

    private Daily parseDailyDTO(DailyDTO dailyDTO) throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        Daily daily = BeanUtils.bean2Another(dailyDTO, Daily.class);
        daily.setYear(DateUtil.year(dailyDTO.getDate()));
        daily.setMonth(DateUtil.month(dailyDTO.getDate()));
        daily.setDay(DateUtil.dayOfMonth(dailyDTO.getDate()));
        return daily;
    }
}
