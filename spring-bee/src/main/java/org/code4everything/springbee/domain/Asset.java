package org.code4everything.springbee.domain;

import com.zhazhapan.util.annotation.SensitiveData;
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
@ApiModel(value = "asset", description = "资产（可流动资金）")
public class Asset implements Serializable {

    @Id
    @ApiModelProperty("资产编号")
    private String id;

    @SensitiveData
    @ApiModelProperty("用户编号（敏感数据）")
    private String userId;

    @ApiModelProperty("可用余额，含小数")
    private Long balance;

    @ApiModelProperty("创建时间")
    private Long createTime;
}
