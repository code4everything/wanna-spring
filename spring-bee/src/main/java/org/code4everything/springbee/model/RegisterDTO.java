package org.code4everything.springbee.model;

import com.zhazhapan.util.annotation.FieldChecking;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "register", description = "注册信息")
public class RegisterDTO implements Serializable {

    @FieldChecking
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @FieldChecking
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @FieldChecking
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @FieldChecking
    @ApiModelProperty(value = "验证码", required = true)
    private String vcode;
}
