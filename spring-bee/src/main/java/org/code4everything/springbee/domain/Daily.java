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
@ApiModel(value = "daily", description = "日常记录")
public class Daily implements Serializable {

    @Id
    @ApiModelProperty("记录编号")
    private String id;

    @SensitiveData
    @ApiModelProperty("用户编号（敏感数据）")
    private String userId;

    @ApiModelProperty("记录的年份")
    private Integer year;

    @ApiModelProperty("记录的月份")
    private Integer month;

    @ApiModelProperty("记录的号数")
    private Integer day;

    @ApiModelProperty("记录时间")
    private Long createTime;

    @ApiModelProperty("评价：0~9，6分以下表现欠佳（反思），6分合格，7分还行，8分优秀，9以上非常优秀")
    private Integer score;

    @ApiModelProperty("天气")
    private String weather;

    @ApiModelProperty("记录内容（可作为日记）")
    private String content;
}
