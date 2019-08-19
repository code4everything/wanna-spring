package org.code4everything.springbee;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import org.code4everything.springbee.domain.Job;
import org.code4everything.springbee.model.JobVO;
import org.junit.Test;

import java.util.Date;

/**
 * @author pantao
 * @since 2018/11/18
 */
public class CommonTest {

    @Test
    public void dateUtil() {
        Date start = new Date();
        System.out.println(DateUtil.formatDate(DateUtil.offsetDay(start, 1)));
    }

    @Test
    public void copy() {
        JobVO jobVO = new JobVO();
        jobVO.setCompany("company");
        Job job = new Job();
        job.setId("123456789");
        BeanUtil.copyProperties(jobVO, job);
        System.out.println(job);
    }
}
