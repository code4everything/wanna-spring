package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.springbee.constant.TestConsts;
import org.code4everything.springbee.dao.DailiesDAO;
import org.code4everything.springbee.dao.DailyDAO;
import org.code4everything.springbee.domain.Dailies;
import org.code4everything.springbee.model.DailiesVO;
import org.code4everything.springbee.service.DailiesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DailiesServiceImplTest {

    @Autowired
    private DailiesService dailiesService;

    @Autowired
    private DailyDAO dailyDAO;

    @Autowired
    private DailiesDAO dailiesDAO;

    @Test
    public void listDailies() {
        List<Dailies> dailies = dailiesService.listDailies(dailyDAO.findAll().get(0).getId());
        Assert.assertNotNull(dailies);
        System.out.println(dailies);
    }

    @Test
    public void updateDailies() {
        DailiesVO dailiesVO = new DailiesVO();
        dailiesVO.setContent(RandomUtil.randomString(12));
        dailiesVO.setEndTime(TestConsts.START_TIME);
        dailiesVO.setStartTime(TestConsts.END_TIME);
        Assert.assertNotNull(dailiesService.updateDailies(dailiesDAO.findAll().get(0).getId(), dailiesVO));
    }

    @Test
    public void remove() {
        dailiesService.remove(dailiesDAO.findAll().get(0).getId());
    }

    @Test
    public void saveDailies() {
        DailiesVO dailiesVO = new DailiesVO();
        dailiesVO.setContent(RandomUtil.randomString(12));
        dailiesVO.setEndTime(TestConsts.START_TIME);
        dailiesVO.setStartTime(TestConsts.END_TIME);
        Assert.assertNotNull(dailiesService.saveDailies(dailyDAO.findAll().get(0).getId(), dailiesVO));
    }
}
