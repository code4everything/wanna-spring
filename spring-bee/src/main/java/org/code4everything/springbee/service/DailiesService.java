package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesDTO;

import java.util.List;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface DailiesService {

    /**
     * 列出日程所有的详细记录
     *
     * @param dailyId 日程编号
     *
     * @return 详细记录列表
     */
    List<Dailies> listDailies(String dailyId);

    /**
     * 更新日程详细记录
     *
     * @param dailiesId 详细记录编号
     * @param dailiesDTO 详细记录内容
     *
     * @return 详细记录
     */
    Dailies updateDailies(String dailiesId, DailiesDTO dailiesDTO);

    /**
     * 删除详细记录
     *
     * @param dailiesId 详细记录编号
     */
    void remove(String dailiesId);

    /**
     * 保存详细记录信息
     *
     * @param dailyId 日程编号
     * @param dailiesDTO 详细记录内容
     *
     * @return 详细记录
     */
    Dailies saveDailies(String dailyId, DailiesDTO dailiesDTO);
}
