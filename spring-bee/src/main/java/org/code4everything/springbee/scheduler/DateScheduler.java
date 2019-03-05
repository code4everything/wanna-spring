package org.code4everything.springbee.scheduler;

import cn.hutool.core.date.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author pantao
 * @since 2019/3/5
 **/
@Component
public class DateScheduler {

    private static Date startOfToday = DateUtil.beginOfDay(new Date());

    public static long getMillisOfTodaysStart() {
        return startOfToday.getTime();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void freeTask() {
        startOfToday = new Date();
    }
}
