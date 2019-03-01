package org.code4everything.springbee.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.boot.annotations.Sealed;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2019/3/1
 **/
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "job", description = "工作日志")
public class Job implements Serializable {

    @Id
    @ApiModelProperty("编号")
    private String id;

    @Sealed
    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("开始工作时间")
    private Long workTimeStart;

    @ApiModelProperty("工作结束时间")
    private Long workTimeEnd;

    @ApiModelProperty("方式：1正常，2加班")
    private String workWay;

    @ApiModelProperty("工作日志")
    private String workDiary;

    @ApiModelProperty("创建时间")
    private Long createTime;
}
