package org.code4everything.springbee.dao;

import org.code4everything.springbee.domain.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/9/13
 **/
@Repository
public interface DocumentDAO extends MongoRepository<Document, String> {

    /**
     * 通过本地路径或访问链接获取文件信息
     *
     * @param localPath 本地路径
     * @param accessUrl 访问链接
     *
     * @return 文件信息
     */
    Document getByLocalPathOrAccessUrl(String localPath, String accessUrl);

    /**
     * 获取文件信息
     *
     * @param accessUrl 访问链接
     *
     * @return 文件信息
     */
    Document getByAccessUrl(String accessUrl);
}
