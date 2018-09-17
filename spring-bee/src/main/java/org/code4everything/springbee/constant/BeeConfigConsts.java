package org.code4everything.springbee.constant;

import com.zhazhapan.modules.constant.ValueConsts;

import java.io.File;

/**
 * @author pantao
 * @since 2018/9/13
 **/
public class BeeConfigConsts {

    /**
     * 自定义配置上传文件保存路径
     */
    public static final String STORAGE_PATH = ValueConsts.USER_HOME + File.separator + "wanna-spring" + File.separator;

    /**
     * 如果需要修改文件的映射路径，还需要修改对应的Controller映射路径
     */
    public static final String DOCUMENT_MAPPING = "/user/document/";

    /**
     * 自定义配置RSA私钥路径
     */
    public static final String PRIVATE_RSA_KEY_PATH = "/Users/pantao/Desktop/temp/rsa/private.key";
}
