package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.base.bean.BaseBean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/9
 */
@Data
@ToString
@Document
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "日常记录详情")
public class Dailies implements BaseBean, Serializable {

    private static final long serialVersionUID = 7412904612383454457L;

    @Id
    @ApiModelProperty("详情编号")
    private String id;

    @ApiModelProperty("日常记录编号")
    private String dailyId;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("详情内容")
    private String content;
}
