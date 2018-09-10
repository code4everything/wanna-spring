package org.code4everything.springbee.model;

import com.zhazhapan.util.annotation.FieldChecking;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author pantao
 * @since 2018/9/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "daily", description = "日程记录")
public class DailyDTO {

    @ApiModelProperty("记录日期（默认为当前日期）")
    private Date date;

    @FieldChecking
    @ApiModelProperty(value = "评分", required = true)
    private Integer score;

    @ApiModelProperty("天气")
    private String weather;

    @ApiModelProperty("记录内容")
    private String content;
}
