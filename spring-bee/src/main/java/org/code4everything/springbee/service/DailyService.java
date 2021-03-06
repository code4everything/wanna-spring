package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyVO;
import org.springframework.scheduling.annotation.Async;

import java.sql.Date;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/22
 */
public interface DailyService {

    /**
     * 检测日程是否存在
     *
     * @param dailyId 日程编号
     *
     * @return 是否存在
     */
    boolean exists(String dailyId);

    /**
     * 获取日程信息
     *
     * @param userId 用户编号
     * @param date 日期
     *
     * @return 日程记录
     */
    Daily getDaily(String userId, Date date);

    /**
     * 列出日程记录
     *
     * @param userId 用户编号
     * @param startDate 开始日期
     * @param endDate 截止日期
     *
     * @return 日程列表
     */
    List<Daily> listDaily(String userId, Date startDate, Date endDate);

    /**
     * 删除日程
     *
     * @param dailyId 日程编号
     */
    @Async
    void remove(String dailyId);

    /**
     * 保存日程
     *
     * @param userId 用户编号
     * @param dailyVO 日程记录内容
     *
     * @return 日程记录
     */
    Daily saveDaily(String userId, DailyVO dailyVO);

    /**
     * 检测日程是否存在
     *
     * @param userId 用户编号
     * @param dailyId 日程编号
     * @param date 日程内容
     *
     * @return 是否存在
     */
    boolean exists(String userId, String dailyId, Date date);

    /**
     * 更新日程记录
     *
     *
     * @param userId
     * @param dailyId 日程编号
     * @param dailyVO 日程内容
     *
     * @return 日程记录
     */
    Daily updateDaily(String userId, String dailyId, DailyVO dailyVO);
}
