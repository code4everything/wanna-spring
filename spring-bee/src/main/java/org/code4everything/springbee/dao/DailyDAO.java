package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Daily;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/9/22
 */
@Repository
public interface DailyDAO extends MongoRepository<Daily, String> {

    /**
     * 获取日程内容
     *
     * @param dailyId 日程编号
     *
     * @return 日程内容
     */
    Daily getById(String dailyId);

    /**
     * 获取日程内容
     *
     * @param userId 用户编号
     * @param date 日期
     *
     * @return 日程内容
     */
    Daily getByUserIdAndDate(String userId, String date);
}
