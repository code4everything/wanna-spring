package org.code4everything.springbee.service;

import com.zhazhapan.util.interfaces.MultipartFileService;
import org.code4everything.springbee.domain.Document;

/**
 * @author pantao
 * @since 2018/9/13
 **/
public interface DocumentService extends MultipartFileService<Document> {

    String getLocalPathByAccessUrl(String accessUrl);
}
