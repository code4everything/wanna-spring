package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Setting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2019/5/10
 **/
@Repository
public interface SettingDAO extends MongoRepository<Setting, String> {

    Setting getByUserId(String userId);
}
