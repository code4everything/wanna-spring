package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/9
 */
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "income", description = "收益明细，包括支出")
public class Income implements Serializable {

    @Id
    @ApiModelProperty("收益编号")
    private String id;

    @ApiModelProperty("资产编号")
    private String assetId;

    @ApiModelProperty(value = "类型：-1支出，1收入", allowableValues = "-1, 1")
    private Integer type;

    @ApiModelProperty(value = "付款方式（1其他，2支付宝，3微信，4银联，5信用卡，6现金）", allowableValues = "0, 1, 2, 3, 4, 5, 6")
    private String way;

    @ApiModelProperty("年份")
    private Integer year;

    @ApiModelProperty("月份")
    private Integer month;

    @ApiModelProperty("号数")
    private Integer day;

    @ApiModelProperty("金额")
    private Long money;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Long createTime;
}
