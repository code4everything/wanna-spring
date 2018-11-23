package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/9/20
 */
@Repository
public interface AssetDAO extends MongoRepository<Asset, String> {

    /**
     * 通过用户编号获取资产信息
     *
     * @param userId 用户编号
     *
     * @return 资产信息
     */
    Asset getByUserId(String userId);
}
