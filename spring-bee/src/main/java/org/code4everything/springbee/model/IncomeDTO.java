package org.code4everything.springbee.model;

import com.zhazhapan.util.annotation.FieldChecking;
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
@ApiModel(value = "incomeDTO", description = "收益信息")
public class IncomeDTO implements Serializable {

    @FieldChecking
    @ApiModelProperty(value = "类型：-1支出，1收入", allowableValues = "-1, 1")
    private Integer type;

    @FieldChecking
    @ApiModelProperty(value = "付款方式（1其他，2支付宝，3微信，4银联，5信用卡，6现金）", allowableValues = "0, 1, 2, 3, 4, 5, 6")
    private String way;

    @FieldChecking
    @ApiModelProperty("日期")
    private Date date;

    @FieldChecking
    @ApiModelProperty("金额")
    private Long money;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("备注")
    private String remark;
}
