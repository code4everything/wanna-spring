package org.code4everything.springbee.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.code4everything.springbee.config.BeeConfigBean;
import org.code4everything.springbee.constant.BeeConfigConsts;

/**
 * @author pantao
 * @since 2018/10/28
 */
public class BeeUtils {

    private static final Logger LOGGER = Logger.getLogger(BeeUtils.class);

    private BeeUtils() {}

    public static BeeConfigBean parseConfigurer(String configFile) {
        BeeConfigBean configBean = null;
        if (FileUtil.exist(configFile)) {
            try {
                configBean = JSONObject.parseObject(FileUtil.readUtf8String(configFile), BeeConfigBean.class);
            } catch (Exception e) {
                LOGGER.warn("load config file error: " + e.getMessage());
            }
        }
        if (Validator.isNull(configBean)) {
            configBean = new BeeConfigBean();
        }
        assert configBean != null;
        // 如果字段值为空，则设置成默认值
        if (Validator.isNull(configBean.getTokenExpired())) {
            configBean.setTokenExpired(BeeConfigConsts.TOKEN_EXPIRED);
        }
        if (Validator.isEmpty(configBean.getBlackPrefixes())) {
            configBean.setBlackPrefixes(BeeConfigConsts.BLACK_PREFIXES);
        }
        if (Validator.isEmpty(configBean.getWhitePrefixes())) {
            configBean.setWhitePrefixes(BeeConfigConsts.WHITE_PREFIXES);
        }
        if (Validator.isEmpty(configBean.getInterceptPrefixes())) {
            configBean.setInterceptPrefixes(BeeConfigConsts.INTERCEPT_PREFIXES);
        }
        if (Validator.isEmpty(configBean.getStoragePath())) {
            configBean.setStoragePath(BeeConfigConsts.STORAGE_PATH);
        }
        return configBean;
    }
}
