package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.BeanUtils;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.DateUtils;
import com.zhazhapan.util.annotation.AopLog;
import com.zhazhapan.util.model.SimpleDateTime;
import org.code4everything.springbee.dao.AssetDAO;
import org.code4everything.springbee.dao.IncomeDAO;
import org.code4everything.springbee.domain.Asset;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeDTO;
import org.code4everything.springbee.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/9/20
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    private final AssetDAO assetDAO;

    private final IncomeDAO incomeDAO;

    @Autowired
    public IncomeServiceImpl(AssetDAO assetDAO, IncomeDAO incomeDAO) {
        this.assetDAO = assetDAO;
        this.incomeDAO = incomeDAO;
    }

    @Override
    @AopLog("查询资产可用余额")
    public Long getAssetBalance(String userId) {
        return getAssetByUserId(userId).getBalance();
    }

    @Override
    @AopLog("删除收益记录")
    public void remove(String incomeId) {
        incomeDAO.deleteById(incomeId);
    }

    @Override
    @AopLog("添加收益记录")
    public Income saveIncome(String userId, IncomeDTO incomeDTO) throws IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Income income = BeanUtils.bean2Another(incomeDTO, Income.class);
        BeanUtils.bean2Another(new SimpleDateTime(incomeDTO.getDate()), income);
        income.setCreateTime(DateUtils.getCurrentTimestamp());
        income.setId(RandomUtil.simpleUUID());
        income.setAssetId(updateAssetBalance(userId, income.getMoney() * income.getType()));
        return incomeDAO.save(income);
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
