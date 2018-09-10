package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/9/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "todo", description = "待办事项")
public class Todo implements Serializable {

    @Id
    @ApiModelProperty("事项编号")
    private String id;

    @ApiModelProperty("用户编号（敏感数据）")
    private String userId;

    @ApiModelProperty("计划事项完成的日期")
    private Date doingDate;

    @ApiModelProperty("事项内容")
    private String content;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "状态：0待完成，1已完成", allowableValues = "0, 1")
    private String status;

    @ApiModelProperty("实际完成时间")
    private Timestamp doneTime;
}
