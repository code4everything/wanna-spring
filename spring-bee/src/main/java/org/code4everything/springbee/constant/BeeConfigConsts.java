package org.code4everything.springbee.constant;

import org.code4everything.boot.base.FileUtils;

import java.io.File;

/**
 * @author pantao
 * @since 2018/9/13
 */
public class BeeConfigConsts {

    /**
     * 会话时长
     */
    public static final int TOKEN_EXPIRED = 30 * 60;

    /**
     * 自定义配置上传文件保存路径
     */
    public static final String STORAGE_PATH = FileUtils.currentWorkDir("document") + File.separator;

    /**
     * 如果需要修改文件的映射路径，还需要修改对应的Controller映射路径
     */
    public static final String DOCUMENT_MAPPING = "/user/document/";

    private BeeConfigConsts() {}
}
