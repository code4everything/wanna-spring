package org.code4everything.springbee.model;

import com.zhazhapan.util.annotation.FieldChecking;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "queryDaily", description = "按时间查询")
public class QueryDailyDTO implements Serializable {

    @FieldChecking
    @ApiModelProperty(value = "年份", required = true)
    private Integer year;

    @FieldChecking
    @ApiModelProperty(value = "月份（0表示列出所有月份）", required = true)
    private Integer month;

    @FieldChecking
    @ApiModelProperty(value = "日期（0表示列出所有日期）", required = true)
    private Integer day;
}
