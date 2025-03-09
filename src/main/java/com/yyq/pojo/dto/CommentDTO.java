package com.yyq.pojo.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long articleId;     // 文章ID
    private Long userId;        // 发表评论的用户ID
    private Long parentId;      // 父评论ID（0 表示根评论）
    private Long targetUserId;  // 被回复的用户ID
    private String content;     // 评论内容
}
