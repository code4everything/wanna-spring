package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeDTO;
import org.code4everything.springbee.model.QueryIncomeDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/20
 */
public interface IncomeService {

    Long getAssetBalance(String userId);

    List<Income> listIncome(String userId, QueryIncomeDTO queryIncomeDTO);

    Income updateIncome(String userId, String incomeId, IncomeDTO incomeDTO) throws InvocationTargetException,
            IllegalAccessException;

    void remove(String incomeId);

    Income saveIncome(String userId, IncomeDTO incomeDTO) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
