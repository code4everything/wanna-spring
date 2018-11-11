package org.code4everything.springbee;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import com.zhazhapan.util.DateUtils;
import org.junit.Test;

import java.sql.Date;

/**
 * @author pantao
 * @since 2018/9/15
 */
public class CommonTest {

    @Test
    public void date() {
        Date date = new Date(System.currentTimeMillis());
        long start = System.currentTimeMillis();
        String day1 = DateUtils.getDay(date);
        long end = System.currentTimeMillis();
        Console.log("use times: {}", end - start);
        start = System.currentTimeMillis();
        int day2 = DateUtil.dayOfMonth(date);
        end = System.currentTimeMillis();
        Console.log("use times: {}", end - start);
        Console.log("day1: {}, day2: {}", day1, day2);
        assert Integer.parseInt(DateUtils.getDay(date)) == DateUtil.dayOfMonth(date);
    }
}
