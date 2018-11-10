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
        if (Validator.isEmpty(configProperty.getBlackListPrefix())) {
            configProperty.setBlackListPrefix(BeeConfigConsts.BLACK_LIST_PREFIX);
        }
        if (Validator.isEmpty(configProperty.getWhiteListPrefix())) {
            configProperty.setWhiteListPrefix(BeeConfigConsts.WHITE_LIST_PREFIX);
        }
        if (Validator.isEmpty(configProperty.getInterceptorListPrefix())) {
            configProperty.setInterceptorListPrefix(BeeConfigConsts.INTERCEPTOR_LIST_PREFIX);
        }
        if (Validator.isEmpty(configProperty.getStoragePath())) {
            configProperty.setStoragePath(BeeConfigConsts.STORAGE_PATH);
        }
        return configProperty;
    }

}
