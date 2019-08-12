package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.springbee.dao.SettingDAO;
import org.code4everything.springbee.domain.Setting;
import org.code4everything.springbee.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author pantao
 * @since 2019/5/10
 */
@Service
public class SettingServiceImpl implements SettingService {

    private final SettingDAO settingDAO;

    @Autowired
    public SettingServiceImpl(SettingDAO settingDAO) {this.settingDAO = settingDAO;}

    @Override
    @LogMethod("保存用户自定义日程评分规则")
    public void updateDailyEvaluateRule(String userId, String dailyEvaluateRule) {
        Setting setting = getSettingByUserId(userId);
        setting.setDailyEvaluateRule(dailyEvaluateRule);
        settingDAO.save(setting);
    }

    @Override
    @LogMethod("获取用户自定义日程评分规则")
    public String getDailyEvaluateRule(String userId) {
        return getSettingByUserId(userId).getDailyEvaluateRule();
    }

    private Setting getSettingByUserId(String userId) {
        Setting setting = settingDAO.getByUserId(userId);
        if (Objects.isNull(setting)) {
            synchronized (SettingServiceImpl.class) {
                setting = settingDAO.getByUserId(userId);
                if (Objects.isNull(setting)) {
                    setting = new Setting();
                    setting.setId(IdUtil.simpleUUID());
                    setting.setUserId(userId);
                    setting.setCreateTime(System.currentTimeMillis());
                    settingDAO.save(setting);
                }
            }
        }
        return setting;
    }
}
