package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.annotation.AopLog;
import com.zhazhapan.util.model.SimpleMultipartFile;
import org.code4everything.springbee.constant.BeeConfigConsts;
import org.code4everything.springbee.dao.DocumentDAO;
import org.code4everything.springbee.domain.Document;
import org.code4everything.springbee.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/9/13
 **/
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDAO documentDAO;

    @Autowired
    public DocumentServiceImpl(DocumentDAO documentDAO) {this.documentDAO = documentDAO;}

    @Override
    @AopLog("通过访问链接获取本地路径")
    public String getLocalPathByAccessUrl(String accessUrl) {
        Document document = documentDAO.getByAccessUrl(accessUrl);
        return Checker.isNull(document) ? "" : document.getAccessUrl();
    }

    @Override
    @AopLog("通过访问链接或本地路径获取文件")
    public Document getBySimpleMultipartFile(SimpleMultipartFile file) {
        String localPath = file.getStoragePath() + file.getFilename();
        String accessUrl = BeeConfigConsts.DOCUMENT_MAPPING + file.getFilename();
        return documentDAO.getByLocalPathOrAccessUrl(localPath, accessUrl);
    }

    @Override
    @AopLog("保存文件")
    public Document saveEntity(SimpleMultipartFile file) {
        Document document = new Document();
        document.setAccessUrl(BeeConfigConsts.DOCUMENT_MAPPING + file.getFilename());
        document.setCreateTime(new Timestamp(System.currentTimeMillis()));
        document.setId(RandomUtil.simpleUUID());
        document.setLocalPath(file.getStoragePath() + file.getFilename());
        document.setSize(file.getSize());
        document.setSuffix(FileExecutor.getFileSuffix(file.getFilename()));
        return documentDAO.save(document);
    }
}
