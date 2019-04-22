package org.code4everything.springbee.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import org.code4everything.springbee.config.BeeConfigBean;
import org.code4everything.springbee.constant.BeeConfigConsts;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Date;
import java.util.Objects;

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

    public static void null2Default(BeeConfigBean configBean) {
        if (Validator.isEmpty(configBean.getStoragePath())) {
            configBean.setStoragePath(BeeConfigConsts.STORAGE_PATH);
        }
        if (Objects.isNull(configBean.getTokenExpired())) {
            configBean.setTokenExpired(BeeConfigConsts.TOKEN_EXPIRED);
        }
    }
}
