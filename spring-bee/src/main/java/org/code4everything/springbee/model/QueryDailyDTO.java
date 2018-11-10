package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "queryDailyDTO", description = "按时间查询")
public class QueryDailyDTO implements Serializable {

    @NotNull
    @ApiModelProperty(value = "年份", required = true)
    private Integer year;

    @Min(0)
    @Max(12)
    @ApiModelProperty(value = "月份（0表示列出所有月份）", required = true)
    private Integer month;

    @Min(0)
    @Max(31)
    @ApiModelProperty(value = "日期（0表示列出所有日期）", required = true)
    private Integer day;
}
