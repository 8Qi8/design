package com.yyq.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("report")
public class Report {
    private Integer id;

    private Integer targetArticleId;

    private String reason;

    private Integer reportUserId;

    private LocalDateTime reportTime;

    private String status; // 枚举："未处理"、"已处理"

    private String result;
}
