package org.code4everything.springbee.util;

import cn.hutool.core.date.DateUtil;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Date;

/**
 * @author pantao
 * @since 2018/10/28
 */
public class BeeUtils {

    private BeeUtils() {}

    public static void betweenStartAndEnd(Criteria criteria, Date start, Date end) {
        Criteria dateGreatThan = Criteria.where("date").gte(DateUtil.formatDate(start));
        Criteria dateLessThan = Criteria.where("date").lte(DateUtil.formatDate(end));
        criteria.andOperator(dateGreatThan, dateLessThan);
    }
}
