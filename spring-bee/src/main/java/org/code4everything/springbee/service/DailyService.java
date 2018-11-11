package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/22
 */
public interface DailyService {

    boolean exists(String dailyId);

    Daily getDaily(String userId, Date date);

    ArrayList<Daily> listDaily(String userId, Date startDate, Date endDate);

    void remove(String dailyId);

    Daily saveDaily(String userId, DailyDTO dailyDTO);

    boolean exists(String userId, String dailyId, DailyDTO dailyDTO);

    Daily updateDaily(String dailyId, DailyDTO dailyDTO);
}
