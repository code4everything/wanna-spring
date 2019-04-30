package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.boot.base.bean.BaseBean;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author pantao
 * @since 2018/9/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "收益信息")
public class IncomeVO implements BaseBean, Serializable {

    @Max(1)
    @Min(-1)
    @ApiModelProperty(value = "类型：-1支出，1收入", allowableValues = "-1, 1")
    private Integer type;

    @NotBlank
    @Length(max = 1)
    @ApiModelProperty(value = "付款方式（1其他，2支付宝，3微信，4银联，5信用卡，6现金）", allowableValues = "1, 2, 3, 4, 5, 6")
    private String way;

    @NotNull
    @ApiModelProperty("日期")
    private Date date;

    @NotNull
    @ApiModelProperty("金额")
    private Long money;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("备注")
    private String remark;
}
