package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesDTO;

import java.lang.reflect.InvocationTargetException;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface DailiesService {

    void remove(String dailiesId);

    Dailies saveDailies(String dailyId, DailiesDTO dailiesDTO) throws IllegalAccessException,
            InvocationTargetException, InstantiationException;
}
