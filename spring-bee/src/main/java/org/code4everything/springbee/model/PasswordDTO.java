package org.code4everything.springbee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "passwordDTO", description = "重置密码")
public class PasswordDTO implements Serializable {

    @Email
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @Length(min = 6, max = 20)
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

    @Length(min = 6, max = 20)
    @ApiModelProperty(value = "验证码", required = true)
    private String vcode;
}
