package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/9/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "dailyDetail", description = "日常记录详情")
public class Dailies {

    @Id
    @ApiModelProperty("详情编号")
    private String id;

    @ApiModelProperty("日常记录编号")
    private String dailyId;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("开始时间")
    private Time startTime;

    @ApiModelProperty("结束时间")
    private Time endTime;

    @ApiModelProperty("详情内容")
    private String content;
}
