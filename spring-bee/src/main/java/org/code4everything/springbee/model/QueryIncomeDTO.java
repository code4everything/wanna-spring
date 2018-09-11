package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author pantao
 * @since 2018/9/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "queryIncome", description = "查询收益详情")
public class QueryIncomeDTO implements Serializable {

    @ApiModelProperty("开始日期（为空时没有开始日期）")
    private Date start;

    @ApiModelProperty("截止日期（为空时没有截止日期）")
    private Date end;

    @ApiModelProperty("分类（为空时查询所有分类）")
    private String category;
}
