package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Dailies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/24
 */
@Repository
public interface DailiesDAO extends MongoRepository<Dailies, String> {

    /**
     * 获取日程详细信息
     *
     * @param dailiesId 日程详细编号
     *
     * @return 日程详细信息
     */
    Dailies getById(String dailiesId);

    /**
     * 列出日程所有的详细信息
     *
     * @param dailyId 日程编号
     *
     * @return 日程详细信息列表
     */
    ArrayList<Dailies> getByDailyId(String dailyId);
}
