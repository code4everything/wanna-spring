package org.code4everything.springbee.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
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
        return Validator.isNull(document) ? "" : document.getAccessUrl();
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
        Document document = new Document();
        document.setAccessUrl(BeeConfigConsts.DOCUMENT_MAPPING + fileBean.getFilename());
        document.setCreateTime(System.currentTimeMillis());
        document.setId(IdUtil.randomUUID());
        document.setLocalPath(SpringBeeApplication.getBeeConfigBean().getStoragePath() + fileBean.getFilename());
        document.setSize(fileBean.getSize());
        document.setSuffix(FileUtil.extName(fileBean.getOriginalFilename()));
        return documentDAO.save(document);
    }
}
