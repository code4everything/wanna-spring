package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.BeanUtils;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.SimpleDateTime;
import org.code4everything.boot.annotations.AopLog;
import org.code4everything.springbee.constant.BeeValueConsts;
import org.code4everything.springbee.dao.AssetDAO;
import org.code4everything.springbee.dao.IncomeDAO;
import org.code4everything.springbee.domain.Asset;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeDTO;
import org.code4everything.springbee.model.QueryIncomeDTO;
import org.code4everything.springbee.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/20
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    private final AssetDAO assetDAO;

    private final IncomeDAO incomeDAO;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public IncomeServiceImpl(AssetDAO assetDAO, IncomeDAO incomeDAO, MongoTemplate mongoTemplate) {
        this.assetDAO = assetDAO;
        this.incomeDAO = incomeDAO;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @AopLog("查询资产可用余额")
    public Long getAssetBalance(String userId) {
        return getAssetByUserId(userId).getBalance();
    }

    @Override
    @AopLog("查询收益记录")
    public List<Income> listIncome(String userId, QueryIncomeDTO queryIncomeDTO) {
        Query query = new Query();
        Criteria criteria = Criteria.where("assetId").is(getAssetByUserId(userId).getId());
        final String y = "year";
        final String m = "month";
        final String d = "day";
        if (Checker.isNotNull(queryIncomeDTO)) {
            List<Criteria> criteriaList = new ArrayList<>(4);
            if (Checker.isNotEmpty(queryIncomeDTO.getCategory())) {
                criteriaList.add(Criteria.where("category").is(queryIncomeDTO.getCategory()));
            }
            if (Checker.isNotEmpty(queryIncomeDTO.getStart())) {
                SimpleDateTime date = new SimpleDateTime(queryIncomeDTO.getStart(), BeeValueConsts.DATE_FORMAT);
                Criteria dayCriteria = Criteria.where(m).is(date.getMonth() + 1).and(d).gte(date.getDay());
                Criteria join = new Criteria().orOperator(Criteria.where(m).gt(date.getMonth() + 1), dayCriteria);
                Criteria monthCriteria = new Criteria().andOperator(Criteria.where(y).is(date.getYear()), join);
                criteriaList.add(new Criteria().orOperator(Criteria.where(y).gt(date.getYear()), monthCriteria));
            }
            if (Checker.isNotEmpty(queryIncomeDTO.getEnd())) {
                SimpleDateTime date = new SimpleDateTime(queryIncomeDTO.getEnd(), BeeValueConsts.DATE_FORMAT);
                Criteria dayCriteria = Criteria.where(m).is(date.getMonth() + 1).and(d).lte(date.getDay());
                Criteria join = new Criteria().orOperator(Criteria.where(m).lt(date.getMonth() + 1), dayCriteria);
                Criteria monthCriteria = new Criteria().andOperator(Criteria.where(y).is(date.getYear()), join);
                criteriaList.add(new Criteria().orOperator(Criteria.where(y).lt(date.getYear()), monthCriteria));
            }
            if (Checker.isNotEmpty(criteriaList)) {
                criteria.andOperator(criteriaList.toArray(new Criteria[0]));
            }
        }
        query.addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, y, m, d, "createTime"));
        return mongoTemplate.find(query, Income.class);
    }

    @Override
    @AopLog("更新收益记录")
    public Income updateIncome(String userId, String incomeId, IncomeDTO incomeDTO) throws InvocationTargetException,
            IllegalAccessException {
        Income income = incomeDAO.getById(incomeId);
        if (Checker.isNull(income)) {
            return null;
        }
        Long changeValue = incomeDTO.getMoney() * incomeDTO.getType() - income.getMoney() * income.getType();
        BeanUtils.bean2Another(incomeDTO, income);
        BeanUtils.bean2Another(new SimpleDateTime(incomeDTO.getDate()), income);
        income.setMonth(income.getMonth() + 1);
        updateAssetBalance(userId, changeValue);
        return incomeDAO.save(income);
    }

    @Override
    @AopLog("删除收益记录")
    public void remove(String userId, String incomeId) {
        Income income = incomeDAO.getById(incomeId);
        if (Checker.isNotNull(income)) {
            updateAssetBalance(userId, income.getMoney() * income.getType() * -1);
            incomeDAO.deleteById(incomeId);
        }
    }

    @Override
    @AopLog("添加收益记录")
    public Income saveIncome(String userId, IncomeDTO incomeDTO) throws IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Income income = BeanUtils.bean2Another(incomeDTO, Income.class);
        BeanUtils.bean2Another(new SimpleDateTime(incomeDTO.getDate()), income);
        income.setMonth(income.getMonth() + 1);
        income.setCreateTime(System.currentTimeMillis());
        income.setId(RandomUtil.simpleUUID());
        income.setAssetId(updateAssetBalance(userId, income.getMoney() * income.getType()));
        return incomeDAO.save(income);
    }

    private String updateAssetBalance(String userId, Long value) {
        Asset asset = getAssetByUserId(userId);
        asset.setBalance(asset.getBalance() + value);
        assetDAO.save(asset);
        return asset.getId();
    }

    private synchronized Asset getAssetByUserId(String userId) {
        // TODO: 2018/9/24 此方法调用频繁，需将结果放入缓存中
        Asset asset = assetDAO.getByUserId(userId);
        if (Checker.isNull(asset)) {
            asset = new Asset();
            asset.setBalance(0L);
            asset.setCreateTime(System.currentTimeMillis());
            asset.setId(RandomUtil.simpleUUID());
            asset.setUserId(userId);
            assetDAO.save(asset);
        }
        return asset;
    }
}
