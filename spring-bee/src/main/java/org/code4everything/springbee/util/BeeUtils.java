package org.code4everything.springbee.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.code4everything.springbee.config.BeeConfigProperty;
import org.code4everything.springbee.constant.BeeConfigConsts;

/**
 * @author pantao
 * @since 2018/10/28
 */
public class BeeUtils {

    private static final Logger LOGGER = Logger.getLogger(BeeUtils.class);

    private BeeUtils() {}

    public static BeeConfigProperty parseConfigurer(String configFile) {
        BeeConfigProperty configProperty = null;
        if (FileUtil.exist(configFile)) {
            try {
                configProperty = JSONObject.parseObject(FileUtil.readUtf8String(configFile), BeeConfigProperty.class);
            } catch (Exception e) {
                LOGGER.warn("load config file error: " + e.getMessage());
            }
        }
        if (Validator.isNull(configProperty)) {
            configProperty = new BeeConfigProperty();
        }
        assert configProperty != null;
        // 设置默认值
        if (Validator.isNull(configProperty.getTokenExpired())) {
            configProperty.setTokenExpired(BeeConfigConsts.TOKEN_EXPIRED);
        }
        if (Validator.isEmpty(configProperty.getBlackPrefixes())) {
            configProperty.setBlackPrefixes(BeeConfigConsts.BLACK_PREFIXES);
        }
        if (Validator.isEmpty(configProperty.getWhitePrefixes())) {
            configProperty.setWhitePrefixes(BeeConfigConsts.WHITE_PREFIXES);
        }
        if (Validator.isEmpty(configProperty.getInterceptPrefixes())) {
            configProperty.setInterceptPrefixes(BeeConfigConsts.INTERCEPT_PREFIXES);
        }
        if (Validator.isEmpty(configProperty.getStoragePath())) {
            configProperty.setStoragePath(BeeConfigConsts.STORAGE_PATH);
        }
        return configProperty;
    }

}
