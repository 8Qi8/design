package com.yyq.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("columns") // 对应数据库表名
public class Columns {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Integer isTop;
    private Integer heat;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
