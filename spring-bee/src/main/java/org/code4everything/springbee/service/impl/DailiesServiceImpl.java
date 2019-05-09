package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.springbee.constant.BeeErrorConsts;
import org.code4everything.springbee.dao.DailiesDAO;
import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesVO;
import org.code4everything.springbee.service.DailiesService;
import org.code4everything.springbee.service.DailyService;
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

    private final DailyService dailyService;

    @Autowired
    public DailiesServiceImpl(DailiesDAO dailiesDAO, DailyService dailyService) {
        this.dailiesDAO = dailiesDAO;
        this.dailyService = dailyService;
    }

    @Override
    @LogMethod("获取所有日程记录详情")
    public List<Dailies> listDailies(String dailyId) {
        return dailiesDAO.getByDailyId(dailyId);
    }

    @Override
    @LogMethod("更新日程记录详情")
    public Dailies updateDailies(String dailiesId, DailiesVO dailiesVO) {
        Dailies dailies = dailiesDAO.getById(dailiesId);
        AssertUtils.throwIfNull(dailies, BeeErrorConsts.DAILIES_NOT_FOUND);
        return dailiesDAO.save(dailiesVO.copyInto(dailies));
    }

    @Override
    @LogMethod("删除日程记录详情")
    public void remove(String dailiesId) {
        dailiesDAO.deleteById(dailiesId);
    }

    @Override
    @LogMethod("添加日程记录详情信息")
    public Dailies saveDailies(String dailyId, DailiesVO dailiesVO) {
        AssertUtils.throwIf(!dailyService.exists(dailyId), BeeErrorConsts.DAILY_NOT_FOUND);
        Dailies dailies = dailiesVO.copyInto(new Dailies());
        dailies.setDailyId(dailyId);
        dailies.setCreateTime(System.currentTimeMillis());
        dailies.setId(IdUtil.simpleUUID());
        return dailiesDAO.save(dailies);
    }
}
