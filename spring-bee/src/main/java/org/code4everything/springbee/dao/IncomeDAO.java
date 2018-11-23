package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Income;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/9/24
 */
@Repository
public interface IncomeDAO extends MongoRepository<Income, String> {

    /**
     * 获取收益信息
     *
     * @param incomeId 记录编号
     *
     * @return 收益消息
     */
    Income getById(String incomeId);
}
