package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/11/18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "todoCountVO", description = "待办事项统计")
public class TodoCountVO implements Serializable {

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("数量")
    private Integer score;
}
