package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author pantao
 * @since 2018/9/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "dailyDTO", description = "日程记录")
public class DailyDTO implements Serializable {

    @ApiModelProperty("记录日期（默认为当前日期）")
    private Date date;

    @Max(10)
    @Min(0)
    @ApiModelProperty(value = "评分", required = true)
    private Integer score;

    @ApiModelProperty("天气")
    private String weather;

    @ApiModelProperty("记录内容")
    private String content;
}
