package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.springbee.BaseTest;
import org.code4everything.springbee.constant.TestConsts;
import org.code4everything.springbee.dao.IncomeDAO;
import org.code4everything.springbee.domain.Income;
import org.code4everything.springbee.model.IncomeDTO;
import org.code4everything.springbee.model.IncomeBillVO;
import org.code4everything.springbee.service.IncomeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IncomeServiceImplTest extends BaseTest {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private IncomeDAO incomeDAO;

    @Test
    public void getAssetBalance() {
        Assert.assertNotNull(incomeService.getAssetBalance(getUser().getId()));
    }

    @Test
    public void listIncome() {
        Long millis = System.currentTimeMillis();
        Date start = new Date(millis - 30 * 24 * 60 * 60 * 1000);
        Date end = new Date(millis);
        List<Income> incomes = incomeService.listIncome(getUser().getId(), "", start, end);
        Assert.assertNotNull(incomes);
        System.out.println(incomes);
    }

    @Test
    public void updateIncome() {
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setCategory(RandomUtil.randomString(4));
        incomeDTO.setDate(new Date(System.currentTimeMillis()));
        incomeDTO.setMoney(RandomUtil.randomLong(Integer.MAX_VALUE));
        incomeDTO.setRemark(RandomUtil.randomString(6));
        incomeDTO.setType(TestConsts.TYPE);
        incomeDTO.setWay(TestConsts.WAY);
        Assert.assertNotNull(incomeService.updateIncome(getUser().getId(), incomeDAO.findAll().get(0).getId(),
                incomeDTO));
    }

    @Test
    public void remove() {
        incomeService.remove(getUser().getId(), incomeDAO.findAll().get(0).getId());
    }

    @Test
    public void saveIncome() {
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setCategory(RandomUtil.randomString(4));
        incomeDTO.setDate(new Date(System.currentTimeMillis()));
        incomeDTO.setMoney(RandomUtil.randomLong(Integer.MAX_VALUE));
        incomeDTO.setRemark(RandomUtil.randomString(6));
        incomeDTO.setType(TestConsts.TYPE);
        incomeDTO.setWay(TestConsts.WAY);
        Assert.assertNotNull(incomeService.saveIncome(getUser().getId(), incomeDTO));
    }

    @Test
    public void listMonth() {
        List<IncomeBillVO> list = incomeService.listMonth(getUser().getId(), "2018-01", "2018-12");
        Assert.assertNotNull(list);
        System.out.println(list);
    }
}
