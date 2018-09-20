package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.annotation.AopLog;
import org.code4everything.springbee.dao.AssetDAO;
import org.code4everything.springbee.domain.Asset;
import org.code4everything.springbee.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/9/20
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    private final AssetDAO assetDAO;

    @Autowired
    public IncomeServiceImpl(AssetDAO assetDAO) {this.assetDAO = assetDAO;}

    @Override
    @AopLog("查询资产可用余额")
    public Long getAssetBalance(String userId) {
        return getAssetByUserId(userId).getBalance();
    }

    private String updateAssetBalance(String userId, Long value) {
        Asset asset = getAssetByUserId(userId);
        asset.setBalance(asset.getBalance() + value);
        return asset.getId();
    }

    private Asset getAssetByUserId(String userId) {
        Asset asset = assetDAO.getByUserId(userId);
        if (Checker.isNull(asset)) {
            asset = new Asset();
            asset.setBalance(0L);
            asset.setCreateTime(new Timestamp(System.currentTimeMillis()));
            asset.setId(RandomUtil.simpleUUID());
            asset.setUserId(userId);
            assetDAO.save(asset);
        }
        return asset;
    }
}
