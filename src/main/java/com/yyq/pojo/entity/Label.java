package com.yyq.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("label") // 对应数据库表名
public class Label {
    private Long id;
    @TableField("name")
    private String name;
    private String heat;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
