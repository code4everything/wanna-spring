package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;
import org.code4everything.springbee.model.DailyDateVO;
import org.code4everything.springbee.model.QueryDailyDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/22
 */
public interface DailyService {

    List<DailyDateVO> listDailyDate(String userId);

    List<Daily> listDaily(String userId, QueryDailyDTO query);

    void remove(String dailyId);

    Daily saveDaily(String userId, DailyDTO dailyDTO) throws InvocationTargetException, NoSuchMethodException,
        InstantiationException, IllegalAccessException;

    boolean exists(String userId, DailyDTO dailyDTO);

    Daily updateDaily(String dailyId, DailyDTO dailyDTO) throws InvocationTargetException,
            IllegalAccessException;
}
