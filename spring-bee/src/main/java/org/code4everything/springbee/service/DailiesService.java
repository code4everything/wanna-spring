package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesDTO;

import java.util.ArrayList;

/**
 * @author pantao
 * @since 2018/9/24
 */
public interface DailiesService {

    ArrayList<Dailies> listDailies(String dailyId);

    Dailies updateDailies(String dailiesId, DailiesDTO dailiesDTO);

    void remove(String dailiesId);

    Dailies saveDailies(String dailyId, DailiesDTO dailiesDTO);
}
