package org.code4everything.springbee.service;

/**
 * @author pantao
 * @since 2019/5/10
 */
public interface SettingService {

    void updateDailyEvaluateRule(String userId, String dailyEvaluateRule);

    String getDailyEvaluateRule(String userId);
}
