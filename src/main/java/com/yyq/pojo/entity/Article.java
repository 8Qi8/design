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
@TableName("article") // 对应数据库表名
public class Article {
    private Long id;
    private String title;
    private String content;
    private Integer heat;
    private Long userId;
    private String summary;
    private String coverImage;
    private Integer views;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long updateId;
    private Integer isTop;
    private Integer likeCount;
}
