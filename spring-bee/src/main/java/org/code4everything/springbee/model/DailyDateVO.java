package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author pantao
 * @since 2018/9/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "dailyDate", description = "记录日期列表")
public class DailyDateVO implements Serializable {

    @ApiModelProperty("记录年份")
    private Integer year;

    @ApiModelProperty("月份列表")
    private List<DailyMonthVO> months;
}
