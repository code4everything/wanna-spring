package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Daily;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/22
 */
@Repository
public interface DailyDAO extends MongoRepository<Daily, String> {

    Daily getById(String dailyId);

    boolean existsByUserIdAndYearAndMonthAndDay(String userId, Integer year, Integer month, Integer day);

    List<Daily> getByUserIdAndYearAndMonth(String userId, Integer year, Integer month);

    Daily getByUserIdAndYearAndMonthAndDay(String userId, Integer year, Integer month, Integer day);

    List<Daily> getByUserIdAndYearAndDay(String userId, Integer year, Integer day);

    List<Daily> getByUserIdAndYear(String userId, Integer year);
}
