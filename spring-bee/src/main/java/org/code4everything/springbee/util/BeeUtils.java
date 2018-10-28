package org.code4everything.springbee.util;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.LoggerUtils;
import org.code4everything.springbee.config.BeeConfigProperty;
import org.code4everything.springbee.constant.BeeConfigConsts;

import java.io.IOException;

/**
 * @author pantao
 * @since 2018/10/28
 */
public class BeeUtils {

    public static BeeConfigProperty parseConfigurer(String configFile) {
        BeeConfigProperty configProperty = null;
        if (Checker.isExists(configFile)) {
            try {
                configProperty = JSONObject.parseObject(FileExecutor.readFile(configFile), BeeConfigProperty.class);
            } catch (IOException e) {
                LoggerUtils.warn("load config file error: " + e.getMessage());
            }
        }
        if (Checker.isNull(configProperty)) {
            configProperty = new BeeConfigProperty();
        }
        if (Checker.isNull(configProperty.getTokenExpired())) {
            configProperty.setTokenExpired(BeeConfigConsts.TOKEN_EXPIRED);
        }
        if (Checker.isEmpty(configProperty.getBlackListPrefix())) {
            configProperty.setBlackListPrefix(BeeConfigConsts.BLACK_LIST_PREFIX);
        }
        if (Checker.isEmpty(configProperty.getWhiteListPrefix())) {
            configProperty.setWhiteListPrefix(BeeConfigConsts.WHITE_LIST_PREFIX);
        }
        if (Checker.isEmpty(configProperty.getInterceptorListPrefix())) {
            configProperty.setInterceptorListPrefix(BeeConfigConsts.INTERCEPTOR_LIST_PREFIX);
        }
        if (Checker.isEmpty(configProperty.getStoragePath())) {
            configProperty.setStoragePath(BeeConfigConsts.STORAGE_PATH);
        }
        return configProperty;
    }

}
