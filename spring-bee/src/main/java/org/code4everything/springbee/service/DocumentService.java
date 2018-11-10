package org.code4everything.springbee.service;

import org.code4everything.boot.service.FileService;
import org.code4everything.springbee.domain.Document;

/**
 * @author pantao
 * @since 2018/9/13
 **/
public interface DocumentService extends FileService<Document> {

    String getLocalPathByAccessUrl(String accessUrl);
}
