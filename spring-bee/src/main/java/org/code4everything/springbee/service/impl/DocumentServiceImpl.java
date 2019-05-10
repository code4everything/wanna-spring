package org.code4everything.springbee.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.boot.web.http.DustFile;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.constant.BeeConfigConsts;
import org.code4everything.springbee.dao.DocumentDAO;
import org.code4everything.springbee.domain.Document;
import org.code4everything.springbee.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    @LogMethod("通过访问链接获取本地路径")
    public String getLocalPathByAccessUrl(String accessUrl) {
        Document document = documentDAO.getByAccessUrl(accessUrl);
        return Objects.isNull(document) ? "" : document.getLocalPath();
    }

    @Override
    @LogMethod("通过访问链接或本地路径获取文件")
    public Document getBy(DustFile dustFile) {
        String localPath = SpringBeeApplication.getBeeConfigBean().getStoragePath() + dustFile.getFilename();
        String accessUrl = BeeConfigConsts.DOCUMENT_MAPPING + dustFile.getFilename();
        return documentDAO.getByLocalPathOrAccessUrl(localPath, accessUrl);
    }

    @Override
    @LogMethod("保存文件")
    public Document save(DustFile dustFile) {
        Document document = getBy(dustFile);
        if (Objects.isNull(document)) {
            document = new Document();
            document.setId(IdUtil.randomUUID());
            document.setCreateTime(System.currentTimeMillis());
            document.setAccessUrl(BeeConfigConsts.DOCUMENT_MAPPING + dustFile.getFilename());
            document.setLocalPath(SpringBeeApplication.getBeeConfigBean().getStoragePath() + dustFile.getFilename());
            document.setSuffix(FileUtil.extName(dustFile.getOriginalFilename()));
        }
        document.setSize(dustFile.getSize());
        return documentDAO.save(document);
    }
}
