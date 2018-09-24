package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.BeanUtils;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.DateUtils;
import com.zhazhapan.util.annotation.AopLog;
import org.code4everything.springbee.dao.DailiesDAO;
import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesDTO;
import org.code4everything.springbee.service.DailiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/24
 */
@Service
public class DailiesServiceImpl implements DailiesService {

    private final DailiesDAO dailiesDAO;

    @Autowired
    public DailiesServiceImpl(DailiesDAO dailiesDAO) {this.dailiesDAO = dailiesDAO;}

    @Override
    @AopLog("获取所有日程记录详情")
    public List<Dailies> listDailies(String dailyId) {
        return dailiesDAO.getByDailyId(dailyId);
    }

    @Override
    @AopLog("更新日程记录详情")
    public Dailies updateDailies(String dailiesId, DailiesDTO dailiesDTO) throws InvocationTargetException,
            IllegalAccessException {
        Dailies dailies = dailiesDAO.getById(dailiesId);
        if (Checker.isNotNull(dailies)) {
            return dailiesDAO.save(BeanUtils.bean2Another(dailiesDTO, dailies));
        }
        return null;
    }

    @Override
    @AopLog("删除日程记录详情")
    public void remove(String dailiesId) {
        dailiesDAO.deleteById(dailiesId);
    }

    @Override
    @AopLog("添加日程记录详情信息")
    public Dailies saveDailies(String dailyId, DailiesDTO dailiesDTO) throws IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Dailies dailies = BeanUtils.bean2Another(dailiesDTO, Dailies.class);
        dailies.setDailyId(dailyId);
        dailies.setCreateTime(DateUtils.getCurrentTimestamp());
        dailies.setId(RandomUtil.simpleUUID());
        return dailiesDAO.save(dailies);
    }
}
