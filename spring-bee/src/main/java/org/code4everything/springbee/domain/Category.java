package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/9/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "category", description = "分类")
public class Category implements Serializable {

    @Id
    @ApiModelProperty("编号")
    private String id;

    @ApiModelProperty("用户编号（敏感数据）")
    private String userId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;
}
