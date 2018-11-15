package org.code4everything.springbee.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.code4everything.boot.annotations.AopLog;
import org.code4everything.springbee.dao.AssetDAO;
import org.code4everything.springbee.dao.IncomeDAO;
import org.code4everything.springbee.domain.Asset;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeDTO;
import org.code4everything.springbee.service.IncomeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018/9/20
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    private final AssetDAO assetDAO;

    private final IncomeDAO incomeDAO;

    private final MongoTemplate mongoTemplate;

    private final RedisTemplate<String, Asset> assetRedisTemplate;

    @Autowired
    public IncomeServiceImpl(AssetDAO assetDAO, IncomeDAO incomeDAO, MongoTemplate mongoTemplate,
                             RedisTemplate<String, Asset> assetRedisTemplate) {
        this.assetDAO = assetDAO;
        this.incomeDAO = incomeDAO;
        this.mongoTemplate = mongoTemplate;
        this.assetRedisTemplate = assetRedisTemplate;
    }

    @Override
    @AopLog("查询资产可用余额")
    public Long getAssetBalance(String userId) {
        return getAssetByUserId(userId).getBalance();
    }

    @Override
    @AopLog("查询收益记录")
    public ArrayList listIncome(String userId, String category, Date start, Date end) {
        Query query = new Query();
        Criteria criteria = Criteria.where("assetId").is(getAssetByUserId(userId).getId());
        Criteria dateGreatThan = Criteria.where("date").gte(DateUtil.formatDate(start));
        Criteria dateLessThan = Criteria.where("date").lte(DateUtil.formatDate(end));
        criteria.andOperator(dateGreatThan, dateLessThan);
        if (StrUtil.isNotEmpty(category)) {
            criteria.andOperator(Criteria.where("category").is(category));
        }
        query.addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, "date", "createTime"));
        return (ArrayList<Income>) mongoTemplate.find(query, Income.class);
    }

    @Override
    @AopLog("更新收益记录")
    public Income updateIncome(String userId, String incomeId, IncomeDTO incomeDTO) {
        Income income = incomeDAO.getById(incomeId);
        if (ObjectUtil.isNull(income)) {
            return null;
        }
        Long changeValue = incomeDTO.getMoney() * incomeDTO.getType() - income.getMoney() * income.getType();
        updateAssetBalance(userId, changeValue);
        return incomeDAO.save(parseIncomeDTO(incomeDTO, income));
    }

    @Override
    @AopLog("删除收益记录")
    public void remove(String userId, String incomeId) {
        Income income = incomeDAO.getById(incomeId);
        if (ObjectUtil.isNotNull(income)) {
            updateAssetBalance(userId, income.getMoney() * income.getType() * -1);
            incomeDAO.deleteById(incomeId);
        }
    }

    @Override
    @AopLog("添加收益记录")
    public Income saveIncome(String userId, IncomeDTO incomeDTO) {
        Income income = parseIncomeDTO(incomeDTO, null);
        income.setCreateTime(System.currentTimeMillis());
        income.setId(IdUtil.simpleUUID());
        income.setAssetId(updateAssetBalance(userId, income.getMoney() * income.getType()));
        return incomeDAO.save(income);
    }

    private String updateAssetBalance(String userId, Long value) {
        Asset asset = getAssetByUserId(userId);
        asset.setBalance(asset.getBalance() + value);
        putAssetForRedis(userId, assetDAO.save(asset));
        return asset.getId();
    }

    private Asset getAssetByUserId(String userId) {
        Asset asset = assetRedisTemplate.opsForValue().get("asset." + userId);
        if (Objects.isNull(asset)) {
            asset = assetDAO.getByUserId(userId);
            if (Objects.isNull(asset)) {
                // 防止出现重复值
                synchronized (IncomeServiceImpl.class) {
                    asset = assetDAO.getByUserId(userId);
                    if (Objects.isNull(asset)) {
                        asset = new Asset();
                        asset.setBalance(0L);
                        asset.setCreateTime(System.currentTimeMillis());
                        asset.setId(IdUtil.simpleUUID());
                        asset.setUserId(userId);
                        assetDAO.save(asset);
                    }
                }
            }
            putAssetForRedis(userId, asset);
        }
        return asset;
    }

    private void putAssetForRedis(String userId, Asset asset) {
        assetRedisTemplate.opsForValue().set("asset." + userId, asset, 10, TimeUnit.MINUTES);
    }

    private Income parseIncomeDTO(IncomeDTO incomeDTO, Income income) {
        if (Objects.isNull(income)) {
            income = new Income();
        }
        BeanUtils.copyProperties(incomeDTO, income);
        income.setDate(DateUtil.formatDate(incomeDTO.getDate()));
        return income;
    }
}
