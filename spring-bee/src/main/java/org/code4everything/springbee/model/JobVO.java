package org.code4everything.springbee.model;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.boot.base.bean.BaseBean;
import org.code4everything.springbee.domain.Job;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2019/3/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "工作日志")
public class JobVO implements Serializable, BaseBean {

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

    @Override
    public <T> T copyInto(T target) {
        if (target instanceof Job) {
            Job job = (Job) target;
            if (StrUtil.isNotEmpty(id)) {
                job.setId(id);
            }
            job.setWorkTimeStart(workTimeStart);
            job.setWorkTimeEnd(workTimeEnd);
            job.setCompany(company);
            job.setWorkWay(workWay);
            job.setWorkDiary(workDiary);
        } else {
            BeanUtils.copyProperties(this, target);
        }
        return target;
    }
}
