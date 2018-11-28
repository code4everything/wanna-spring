package org.code4everything.springbee.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.bean.ResponseResult;
import org.code4everything.boot.web.HttpUtils;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.domain.Document;
import org.code4everything.springbee.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author pantao
 * @since 2018/9/13
 **/
@RestController
@RequestMapping("/user/document")
@Api(tags = "文件资源接口")
public class DocumentController extends BeeBaseController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataTypeClass = MultipartFile.class)
    public ResponseResult<Document> upload(@RequestBody MultipartFile file) {
        String storagePath = SpringBeeApplication.getBeeConfigBean().getStoragePath();
        return HttpUtils.upload(documentService, file, storagePath, true).encode();
    }

    @GetMapping("/**")
    @ApiOperation("获取文件资源")
    public ResponseEntity<InputStreamSource> get(HttpServletRequest request) throws IOException {
        return HttpUtils.responseFile(documentService, request);
    }
}
