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

    Dailies getById(String dailiesId);

    ArrayList<Dailies> getByDailyId(String dailyId);
}
