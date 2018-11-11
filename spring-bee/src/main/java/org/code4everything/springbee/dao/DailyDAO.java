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

    Daily getById(String dailyId);

    Daily getByUserIdAndDate(String userId, String date);
}
