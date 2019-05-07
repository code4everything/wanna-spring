package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.boot.base.bean.BaseBean;
import org.code4everything.boot.base.encoder.Sealed;
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
@ApiModel(description = "日常记录")
public class Daily implements BaseBean, Serializable {

    @Id
    @ApiModelProperty("记录编号")
    private String id;

    @Sealed
    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("记录的日期")
    private String date;

    @ApiModelProperty("记录时间")
    private Long createTime;

    @ApiModelProperty("评价：0~10，6分以下表现欠佳，6分合格，7分还行，8分优秀，9以上非常优秀")
    private Integer score;

    @ApiModelProperty("天气")
    private String weather;

    @ApiModelProperty("记录内容（可作为日记）")
    private String content;
}
