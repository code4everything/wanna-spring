package org.code4everything.springbee.web;

import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.NetUtils;
import com.zhazhapan.util.model.ResultObject;
import com.zhazhapan.util.model.SimpleMultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.domain.Document;
import org.code4everything.springbee.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.MediaType;
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
@Api(value = "/user/document", description = "文档接口")
public class DocumentController extends BeeBaseController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataTypeClass = MultipartFile.class)
    public ResultObject<Document> upload(@RequestBody MultipartFile file) throws IOException {
        SimpleMultipartFile multipartFile = new SimpleMultipartFile();
        if (file.getSize() > ValueConsts.MB) {
            return new ResultObject<>(400, "文件不能大于1MB");
        }
        String storagePath = SpringBeeApplication.getBeeConfigBean().getStoragePath();
        multipartFile.setSize(file.getSize()).setStoragePath(storagePath).setOriginalFilename(file.getOriginalFilename());
        ResultObject<Document> resultObject = NetUtils.upload(file.getBytes(), multipartFile, documentService);
        setSensitiveData(resultObject.data);
        return resultObject;
    }

    @GetMapping("/**")
    @ApiOperation("获取文件资源")
    public ResponseEntity<InputStreamSource> get(HttpServletRequest request) throws IOException {
        String localPath = documentService.getLocalPathByAccessUrl(request.getServletPath());
        FileSystemResource file = null;
        if (Checker.isNotEmpty(localPath)) {
            file = new FileSystemResource(localPath);
        }
        if (Checker.isNull(file)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentLength(file.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(file.getInputStream()));
    }
}
