package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.boot.base.bean.BaseBean;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "注册信息")
public class RegisterVO implements BaseBean, Serializable {

    @NotBlank
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @Email
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @Length(min = 6, max = 20)
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @Length(min = 6, max = 6)
    @ApiModelProperty(value = "验证码", required = true)
    private String vcode;
}
