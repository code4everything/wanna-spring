package org.code4everything.springbee.service;

import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeDTO;

import java.lang.reflect.InvocationTargetException;

/**
 * @author pantao
 * @since 2018/9/20
 */
public interface IncomeService {

    Long getAssetBalance(String userId);

    Income saveIncome(String userId, IncomeDTO incomeDTO) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
