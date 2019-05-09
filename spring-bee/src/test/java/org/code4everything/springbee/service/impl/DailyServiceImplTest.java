package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.code4everything.springbee.BaseTest;
import org.code4everything.springbee.dao.DailyDAO;
import org.code4everything.springbee.domain.Daily;
import org.code4everything.springbee.model.DailyVO;
import org.code4everything.springbee.service.DailyService;
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
public class DailyServiceImplTest extends BaseTest {

    @Autowired
    private DailyService dailyService;

    @Autowired
    private DailyDAO dailyDAO;

    @Test
    public void exists() {
        assert dailyService.exists(dailyDAO.findAll().get(0).getId());
    }

    @Test
    public void listDaily() {
        Long millis = System.currentTimeMillis();
        Date start = new Date(millis + 30 * 24 * 60 * 60 * 1000);
        Date end = new Date(millis);
        List<Daily> dailies = dailyService.listDaily(getUser().getId(), start, end);
        Assert.assertNotNull(dailies);
        System.out.println(dailies);
    }

    @Test
    public void remove() {
        dailyService.remove(IdUtil.simpleUUID());
    }

    @Test
    public void saveDaily() {
        DailyVO dailyVO = new DailyVO();
        dailyVO.setContent(RandomUtil.randomString(9));
        dailyVO.setDate(new Date(System.currentTimeMillis()));
        dailyVO.setScore(RandomUtil.randomInt(10));
        dailyVO.setWeather(RandomUtil.randomString(3));
        Assert.assertNotNull(dailyService.saveDaily(getUser().getId(), dailyVO));
    }

    @Test
    public void exists1() {
        DailyVO dailyVO = new DailyVO();
        dailyVO.setDate(new Date(System.currentTimeMillis()));
        dailyService.exists(getUser().getId(), "", dailyVO.getDate());
    }

    @Test
    public void updateDaily() {
        DailyVO dailyVO = new DailyVO();
        dailyVO.setContent(RandomUtil.randomString(9));
        dailyVO.setDate(new Date(System.currentTimeMillis()));
        dailyVO.setScore(RandomUtil.randomInt(10));
        dailyVO.setWeather(RandomUtil.randomString(3));
        dailyService.updateDaily(getUser().getId(), dailyDAO.findAll().get(0).getId(), dailyVO);
    }
}
