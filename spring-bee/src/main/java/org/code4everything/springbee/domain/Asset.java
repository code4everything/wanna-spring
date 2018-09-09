package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/9/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "asset", description = "资产（可流动资金）")
public class Asset {

    @Id
    @ApiModelProperty("资产编号")
    private String id;

    @ApiModelProperty("用户编号（敏感数据）")
    private String userId;

    @ApiModelProperty("可用余额，含小数")
    private Long balance;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;
}
