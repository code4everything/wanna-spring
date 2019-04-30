package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.springbee.dao.DailiesDAO;
import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesVO;
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
    @LogMethod("获取所有日程记录详情")
    public List<Dailies> listDailies(String dailyId) {
        return dailiesDAO.getByDailyId(dailyId);
    }

    @Override
    @LogMethod("更新日程记录详情")
    public Dailies updateDailies(String dailiesId, DailiesVO dailiesVO) {
        Dailies dailies = dailiesDAO.getById(dailiesId);
        if (ObjectUtil.isNotNull(dailies)) {
            BeanUtils.copyProperties(dailiesVO, dailies);
            return dailiesDAO.save(dailies);
        }
        return null;
    }

    @Override
    @LogMethod("删除日程记录详情")
    public void remove(String dailiesId) {
        dailiesDAO.deleteById(dailiesId);
    }

    @Override
    @LogMethod("添加日程记录详情信息")
    public Dailies saveDailies(String dailyId, DailiesVO dailiesVO) {
        Dailies dailies = new Dailies();
        BeanUtils.copyProperties(dailiesVO, dailies);
        dailies.setDailyId(dailyId);
        dailies.setCreateTime(System.currentTimeMillis());
        dailies.setId(IdUtil.simpleUUID());
        return dailiesDAO.save(dailies);
    }
}
