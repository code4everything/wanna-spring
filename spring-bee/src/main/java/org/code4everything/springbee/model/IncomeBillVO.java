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
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "incomeBillVO", description = "月账单")
public class IncomeBillVO implements Serializable {

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("支出总计")
    private Long money;
}
