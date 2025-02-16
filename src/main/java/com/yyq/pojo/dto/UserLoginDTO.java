package com.yyq.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {
    // 用户名
    private String username;
    // 密码
    private String password;
    // 验证码
    private String verificationCode;
    // 手机号
    private String phone;
    // 手机验证码
    private String phoneCode;
    // 个人简介
    private String description;
    // 头像
    private String avatar;
    // 性别
    private Integer sex;
}
