package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeBillVO;
import org.code4everything.springbee.model.IncomeVO;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/20
 */
public interface IncomeService {

    /**
     * 获取资产余额
     *
     * @param userId 用户编号
     *
     * @return 资产余额
     */
    Long getAssetBalance(String userId);

    /**
     * 列出收益记录
     *
     * @param userId 用户编号
     * @param category 分类
     * @param start 开始日期
     * @param end 截止日期
     *
     * @return 收益记录列表
     */
    List<Income> listIncome(String userId, String category, Date start, Date end);

    /**
     * 列出每年的支出总计
     *
     * @param userId 用户编号
     * @param startYear 开始年份
     * @param endYear 截止年份
     *
     * @return 支出总计列表
     */
    List<IncomeBillVO> listYear(String userId, Integer startYear, Integer endYear);

    /**
     * 列出每月的支出总计
     *
     * @param userId 用户编号
     * @param startMonth 开始月份，格式：2018-06
     * @param endMonth 截止月份，格式：2018-12
     *
     * @return 支出总计列表
     */
    List<IncomeBillVO> listMonth(String userId, String startMonth, String endMonth);

    /**
     * 更新收益信息
     *
     * @param userId 用户编号
     * @param incomeId 收益记录编号
     * @param incomeVO 收益信息
     *
     * @return 收益信息
     */
    Income updateIncome(String userId, String incomeId, IncomeVO incomeVO);

    /**
     * 删除收益记录
     *
     * @param userId 用户编号
     * @param incomeId 收益记录编号
     */
    @Async
    void remove(String userId, String incomeId);

    /**
     * 保存收益记录
     *
     * @param userId 用户编号
     * @param incomeVO 收益信息
     *
     * @return 收益信息
     */
    Income saveIncome(String userId, IncomeVO incomeVO);
}
