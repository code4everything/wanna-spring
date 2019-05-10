package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.base.bean.BaseBean;
import org.code4everything.boot.base.encoder.Sealed;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/12
 */
@Data
@ToString
@Document
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分类")
public class Category implements BaseBean, Serializable {

    private static final long serialVersionUID = 8409185108165215228L;

    @Id
    @ApiModelProperty("编号")
    private String id;

    @Sealed
    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("创建时间")
    private Long createTime;
}
