package org.code4everything.springbee.service;

import org.code4everything.boot.service.FileService;
import org.code4everything.springbee.domain.Document;

/**
 * @author pantao
 * @since 2018/9/13
 **/
public interface DocumentService extends FileService<Document> {

    /**
     * 获取文件本地路径
     *
     * @param accessUrl 访问链接
     *
     * @return 本地路径
     */
    String getLocalPathByAccessUrl(String accessUrl);
}
