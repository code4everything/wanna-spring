package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesDTO;

import java.lang.reflect.InvocationTargetException;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface DailiesService {

    Dailies saveDailies(String dailyId, DailiesDTO dailiesDTO) throws IllegalAccessException,
            InvocationTargetException, InstantiationException;
}
