package org.code4everything.springbee.constant;

import org.code4everything.boot.base.FileUtils;

import java.io.File;

/**
 * @author pantao
 * @since 2018/9/13
 **/
public class BeeConfigConsts {

    /**
     * 会话时长
     */
    public static final int TOKEN_EXPIRED = BeeValueConsts.THIRTY_MINUTES;

    /**
     * 白名单路径（放行）
     */
    public static final String[] WHITE_PREFIXES = {"/common", "/user/password/reset", "/user/register", "/user" +
            "/login"};

    /**
     * 拦截路径
     */
    public static final String[] INTERCEPT_PREFIXES = {"/user"};

    /**
     * 黑名单路径
     */
    public static final String[] BLACK_PREFIXES = {"/swagger-ui.html", "/v2/api-docs"};

    /**
     * 自定义配置上传文件保存路径
     */
    public static final String STORAGE_PATH = FileUtils.currentWorkDir() + File.separator + "document" + File.separator;

    /**
     * 如果需要修改文件的映射路径，还需要修改对应的Controller映射路径
     */
    public static final String DOCUMENT_MAPPING = "/user/document/";

    /**
     * 自定义配置RSA私钥路径（可定义绝对路径）
     */
    public static final String PRIVATE_RSA_KEY_PATH = "classpath:/rsa/private.key";

    private BeeConfigConsts() {}
}
