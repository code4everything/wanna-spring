package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.code4everything.boot.bean.BaseBean;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author pantao
 * @since 2019/2/12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "todoDTO", description = "待办事项")
public class TodoDTO implements BaseBean, Serializable {

    @NotBlank
    @ApiModelProperty(value = "计划完成日期", required = true)
    private String doingDate;

    @NotBlank
    @ApiModelProperty(value = "事项内容", required = true)
    private String content;

    @ApiModelProperty("日期偏移")
    private Integer offset;

    @ApiModelProperty("重复次数")
    private Integer repeat;

    public int getOffsetWell() {
        return Objects.isNull(offset) || offset < 0 ? 0 : offset;
    }

    public int getRepeatWell() {
        return Objects.isNull(repeat) || repeat < 0 ? 0 : repeat;
    }
}
