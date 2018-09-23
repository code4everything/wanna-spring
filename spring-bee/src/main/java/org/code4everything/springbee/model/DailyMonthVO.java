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
@ApiModel(value = "dailyMonth", description = "记录月份列表")
public class DailyMonthVO implements Serializable {

    @ApiModelProperty("记录月份")
    private Integer month;

    @ApiModelProperty("日期列表")
    private List<Integer> days;
}
