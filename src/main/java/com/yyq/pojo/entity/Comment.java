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
@TableName("comment") // 对应数据库表名
public class Comment {
    private Long id;
    private Long articleId;
    private Long userId;
    private Long parentId;
    private Long targetUserId;
    private String content;
    private LocalDateTime createTime;


}
