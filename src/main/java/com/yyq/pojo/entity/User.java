package com.yyq.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user") // 对应数据库表名
public class User {
    private Long id;

    @TableField("username") // 表字段映射
    private String username;
    //密码
    private String password;
    //手机号
    private String phone;
   //头像
    private String avatar;
    //性别
    private Integer sex;
    //个人简介
    private String description;
    //注册时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
    //热度
    private Integer heat;
    // 角色
    private Integer role;
}
