package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.boot.bean.BaseBean;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2019/3/1
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "jobDTO", description = "工作日志")
public class JobDTO implements Serializable, BaseBean {

    @ApiModelProperty("编号")
    private String id;

    @ApiModelProperty("开始工作时间")
    private Long workTimeStart;

    @ApiModelProperty("工作结束时间")
    private Long workTimeEnd;

    @ApiModelProperty("公司")
    private String company;

    @ApiModelProperty("方式：1正常，2加班")
    private String workWay;

    @ApiModelProperty("工作日志")
    private String workDiary;
}
