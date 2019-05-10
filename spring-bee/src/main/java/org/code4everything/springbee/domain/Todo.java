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
 * @since 2018/9/9
 */
@Data
@ToString
@Document
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "待办事项")
public class Todo implements BaseBean, Serializable {

    private static final long serialVersionUID = -7735706142745311409L;

    @Id
    @ApiModelProperty("事项编号")
    private String id;

    @Sealed
    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("计划事项完成的日期")
    private String doingDate;

    @ApiModelProperty("事项内容")
    private String content;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty(value = "状态：0待完成，1已完成", allowableValues = "0, 1")
    private String status;

    @ApiModelProperty("实际完成时间")
    private Long doneTime;
}
