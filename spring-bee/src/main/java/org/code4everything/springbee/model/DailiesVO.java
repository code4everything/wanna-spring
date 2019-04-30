package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.boot.base.bean.BaseBean;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "日程详情")
public class DailiesVO implements BaseBean, Serializable {

    @NotBlank
    @ApiModelProperty("开始时间")
    private String startTime;

    @NotBlank
    @ApiModelProperty("结束时间")
    private String endTime;

    @NotBlank
    @ApiModelProperty("详情内容")
    private String content;
}
