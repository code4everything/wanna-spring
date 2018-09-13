package org.code4everything.springbee.web;

import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.NetUtils;
import com.zhazhapan.util.model.ResultObject;
import com.zhazhapan.util.model.SimpleMultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.code4everything.springbee.constant.BeeConfigConsts;
import org.code4everything.springbee.domain.Document;
import org.code4everything.springbee.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author pantao
 * @since 2018/9/13
 **/
@RestController
@RequestMapping("/user/document")
@Api(value = "/user/document", description = "文档接口")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public ResultObject<Document> upload(@RequestBody MultipartFile file) throws IOException {
        SimpleMultipartFile multipartFile = new SimpleMultipartFile();
        if (file.getSize() > ValueConsts.MB) {
            return new ResultObject<>(400, "文件不能大于1MB");
        }
        multipartFile.setSize(file.getSize()).setStoragePath(BeeConfigConsts.STORAGE_PATH).setOriginalFilename(file.getOriginalFilename());
        return NetUtils.upload(file.getBytes(), multipartFile, documentService);
    }
}
