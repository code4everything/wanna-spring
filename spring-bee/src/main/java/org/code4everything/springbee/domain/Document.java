package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.base.bean.BaseDomain;
import org.code4everything.boot.base.encoder.Sealed;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/13
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "文件")
@org.springframework.data.mongodb.core.mapping.Document
public class Document implements BaseDomain {

    private static final long serialVersionUID = 6805524282169955828L;

    @Id
    @ApiModelProperty("文件编号")
    private String id;

    @Sealed
    @ApiModelProperty("文件本地路径")
    private String localPath;

    @ApiModelProperty("访问链接")
    private String accessUrl;

    @ApiModelProperty("文件后缀")
    private String suffix;

    @ApiModelProperty("上传时间")
    private Long createTime;

    @ApiModelProperty("文件大小")
    private Long size;

    @Override
    public Serializable primaryKey() {
        return id;
    }
}
