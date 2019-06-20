package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.base.bean.BaseDomain;
import org.code4everything.boot.base.encoder.Sealed;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2019/5/10
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户配置")
public class Setting implements BaseDomain {

    private static final long serialVersionUID = -4891805531653159414L;

    @Id
    @ApiModelProperty("编号")
    private String id;

    @Sealed
    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("创建时间")
    private Long createTime;

    // ---------------------收入支出模块-------------------

    @ApiModelProperty(value = "默认查询时长（天）", allowableValues = "0, 7, 30")
    private Integer incomeDayLength;

    // ---------------------待办事项模块--------------------------------

    @ApiModelProperty(value = "默认查询时长（天）", allowableValues = "0, 7, 30")
    private Integer todoDayLength;


    // -------------------日程模块----------------------

    @ApiModelProperty("日程评分规则")
    private String dailyEvaluateRule;

    @ApiModelProperty(value = "默认查询时长（天）", allowableValues = "0, 7, 30")
    private Integer dailyDayLength;

    @Override
    public Serializable primaryKey() {
        return id;
    }
}
