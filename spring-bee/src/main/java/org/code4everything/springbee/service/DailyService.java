package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyDTO;

import java.lang.reflect.InvocationTargetException;

/**
 * @author pantao
 * @since 2018/9/22
 */
public interface DailyService {

    void remove(String dailyId);

    Daily saveDaily(String userId, DailyDTO dailyDTO) throws InvocationTargetException, NoSuchMethodException,
        InstantiationException, IllegalAccessException;

    boolean exists(String userId, DailyDTO dailyDTO);

    Daily updateDaily(String dailyId, DailyDTO dailyDTO) throws InvocationTargetException,
            IllegalAccessException;
}
