package org.code4everything.springbee.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.code4everything.boot.annotations.AopLog;
import org.code4everything.boot.bean.MultipartFileBean;
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
    @AopLog("通过访问链接获取本地路径")
    public String getLocalPathByAccessUrl(String accessUrl) {
        Document document = documentDAO.getByAccessUrl(accessUrl);
        return Objects.isNull(document) ? "" : document.getAccessUrl();
    }

    @Override
    @AopLog("通过访问链接或本地路径获取文件")
    public Document getBy(MultipartFileBean fileBean) {
        String localPath = SpringBeeApplication.getBeeConfigBean().getStoragePath() + fileBean.getFilename();
        String accessUrl = BeeConfigConsts.DOCUMENT_MAPPING + fileBean.getFilename();
        return documentDAO.getByLocalPathOrAccessUrl(localPath, accessUrl);
    }

    @Override
    @AopLog("保存文件")
    public Document save(MultipartFileBean fileBean) {
        Document document = getBy(fileBean);
        if (Objects.isNull(document)) {
            document = new Document();
            document.setId(IdUtil.randomUUID());
            document.setCreateTime(System.currentTimeMillis());
            document.setAccessUrl(BeeConfigConsts.DOCUMENT_MAPPING + fileBean.getFilename());
            document.setLocalPath(SpringBeeApplication.getBeeConfigBean().getStoragePath() + fileBean.getFilename());
            document.setSuffix(FileUtil.extName(fileBean.getOriginalFilename()));
        }
        document.setSize(fileBean.getSize());
        return documentDAO.save(document);
    }
}
