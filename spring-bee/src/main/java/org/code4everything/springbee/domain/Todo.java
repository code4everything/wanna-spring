package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.boot.annotation.Sealed;
import org.code4everything.boot.bean.BaseBean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/9
 */
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "todo", description = "待办事项")
public class Todo implements BaseBean, Serializable {

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
