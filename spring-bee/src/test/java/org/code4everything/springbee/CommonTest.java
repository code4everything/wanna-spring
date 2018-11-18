package org.code4everything.springbee;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.util.Date;

/**
 * @author pantao
 * @since 2018/11/18
 **/
public class CommonTest {

    @Test
    public void dateUtil() {
        Date start = new Date();
        System.out.println(DateUtil.formatDate(DateUtil.offsetDay(start, 1)));
    }
}
