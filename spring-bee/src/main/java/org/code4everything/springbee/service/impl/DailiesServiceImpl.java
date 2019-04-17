package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import org.code4everything.boot.annotation.AopLog;
import org.code4everything.springbee.dao.DailiesDAO;
import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesDTO;
import org.code4everything.springbee.service.DailiesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Dailies updateDailies(String dailiesId, DailiesDTO dailiesDTO) {
        Dailies dailies = dailiesDAO.getById(dailiesId);
        if (ObjectUtil.isNotNull(dailies)) {
            BeanUtils.copyProperties(dailiesDTO, dailies);
            return dailiesDAO.save(dailies);
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
    public Dailies saveDailies(String dailyId, DailiesDTO dailiesDTO) {
        Dailies dailies = new Dailies();
        BeanUtils.copyProperties(dailiesDTO, dailies);
        dailies.setDailyId(dailyId);
        dailies.setCreateTime(System.currentTimeMillis());
        dailies.setId(IdUtil.simpleUUID());
        return dailiesDAO.save(dailies);
    }
}
