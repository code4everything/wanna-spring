package org.code4everything.springbee.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Strings;
import org.code4everything.boot.base.constant.StringConsts;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.springbee.constant.BeeErrorConsts;
import org.code4everything.springbee.dao.AssetDAO;
import org.code4everything.springbee.dao.IncomeDAO;
import org.code4everything.springbee.domain.Asset;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeBillVO;
import org.code4everything.springbee.model.IncomeVO;
import org.code4everything.springbee.service.IncomeService;
import org.code4everything.springbee.util.BeeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018/9/20
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    private static final String HYPHEN = StringConsts.Sign.HYPHEN;

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
    @LogMethod("查询资产可用余额")
    public Long getAssetBalance(String userId) {
        return getAssetByUserId(userId).getBalance();
    }

    @Override
    @LogMethod("查询收益记录")
    public List<Income> listIncome(String userId, String category, Date start, Date end) {
        Query query = new Query();
        Criteria criteria = Criteria.where("assetId").is(getAssetByUserId(userId).getId());
        BeeUtils.betweenStartAndEnd(criteria, start, end);
        if (StrUtil.isNotEmpty(category)) {
            criteria.andOperator(Criteria.where("category").is(category));
        }
        query.addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, "date", "createTime"));
        return mongoTemplate.find(query, Income.class);
    }

    @Override
    @LogMethod("查询年度账单")
    public List<IncomeBillVO> listYear(String userId, Integer startYear, Integer endYear) {
        List<IncomeBillVO> list = new ArrayList<>();
        if (startYear > endYear) {
            return list;
        }
        while (startYear <= endYear) {
            IncomeBillVO billVO = new IncomeBillVO();
            billVO.setDate(String.valueOf(startYear));
            Date start = DateUtil.parseDate(billVO.getDate() + HYPHEN + "01" + HYPHEN + "01");
            setBill(list, billVO, userId, start, DateUtil.endOfYear(start));
            startYear++;
        }
        return list;
    }

    @Override
    @LogMethod("查询月度账单")
    public List<IncomeBillVO> listMonth(String userId, String startMonth, String endMonth) {
        List<IncomeBillVO> list = new ArrayList<>();
        if (startMonth.compareTo(endMonth) > 0) {
            return list;
        }
        // 解析开始日期的年和月
        String[] starts = startMonth.split(HYPHEN);
        int yearStart = Integer.parseInt(starts[0]);
        int monthStart = Integer.parseInt(starts[1]);
        // 解析截止日期的年和月
        String[] ends = endMonth.split(HYPHEN);
        int yearEnd = Integer.parseInt(ends[0]);
        int monthEnd = Integer.parseInt(ends[1]);
        // 统计每个月的中支出
        while (yearStart < yearEnd || (yearStart == yearEnd && monthStart <= monthEnd)) {
            IncomeBillVO billVO = new IncomeBillVO();
            String month = yearStart + HYPHEN + Strings.padStart(String.valueOf(monthStart), 2, '0');
            billVO.setDate(month);
            Date start = DateUtil.parseDate(month + HYPHEN + "01");
            setBill(list, billVO, userId, start, DateUtil.endOfMonth(start));
            if (++monthStart > 12) {
                yearStart++;
                monthStart = 1;
            }
        }
        return list;
    }

    @Override
    @LogMethod("更新收益记录")
    public Income updateIncome(String userId, String incomeId, IncomeVO incomeVO) {
        Income income = incomeDAO.getById(incomeId);
        AssertUtils.throwIfNull(income, BeeErrorConsts.INCOME_NOT_FOUND);
        // 计算改变的资产总值
        Long changeValue = incomeVO.getMoney() * incomeVO.getType() - income.getMoney() * income.getType();
        updateAssetBalance(userId, changeValue);
        return incomeDAO.save(income.copyFrom(incomeVO));
    }

    @Override
    @LogMethod("删除收益记录")
    public void remove(String userId, String incomeId) {
        Income income = incomeDAO.getById(incomeId);
        if (ObjectUtil.isNotNull(income)) {
            updateAssetBalance(userId, income.getMoney() * income.getType() * -1);
            incomeDAO.deleteById(incomeId);
        }
    }

    @Override
    @LogMethod("添加收益记录")
    public Income saveIncome(String userId, IncomeVO incomeVO) {
        Income income = new Income().copyFrom(incomeVO);
        income.setCreateTime(System.currentTimeMillis());
        income.setId(IdUtil.simpleUUID());
        income.setAssetId(updateAssetBalance(userId, income.getMoney() * income.getType()));
        return incomeDAO.save(income);
    }

    private void setBill(List<IncomeBillVO> list, IncomeBillVO billVO, String userId, Date start, Date end) {
        List<Income> incomes = listIncome(userId, "", start, end);
        long money = 0;
        for (Income income : incomes) {
            if (income.getType() == -1) {
                money += income.getMoney();
            }
        }
        billVO.setMoney(money);
        list.add(billVO);
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
}
