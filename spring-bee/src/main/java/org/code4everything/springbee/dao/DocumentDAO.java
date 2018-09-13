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

    Document getByLocalPathOrAccessUrl(String localPath, String accessUrl);

    Document getByAccessUrl(String accessUrl);
}
