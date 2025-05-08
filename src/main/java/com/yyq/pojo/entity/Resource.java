package com.yyq.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("resource") // 数据库表名
public class Resource {

    private Integer id;

    private String title;

    private String description;

    private String fileUrl;

    private Integer uploaderId;

    private LocalDateTime uploadTime;

    private Integer downloadCount;
}