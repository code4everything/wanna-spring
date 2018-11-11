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
 * @since 2018/9/9
 */
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "user", description = "用户")
public class User implements Serializable {

    @Id
    @Sealed
    @ApiModelProperty("用户编号（敏感数据）")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像地址")
    private String avatar;

    @Sealed
    @ApiModelProperty("密码（敏感数据)")
    private String password;

    @ApiModelProperty("个人简介")
    private String bio;

    @ApiModelProperty(value = "性别：0未知，1男，2女", allowableValues = "0, 1, 2")
    private String gender;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty(value = "用户状态（0冻结，1待激活，2限制登录，7正常）", allowableValues = "0, 1, 2, 7")
    private String status;

    @ApiModelProperty("最近一次登录时间")
    private Long loginTime;

    @ApiModelProperty("注册时间")
    private Long createTime;
}
