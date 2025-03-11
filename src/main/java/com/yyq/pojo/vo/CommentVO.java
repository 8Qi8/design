package com.yyq.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentVO {
    private Long id;
    private Long articleId; // 文章ID
    private String content;
    private Long parentId; // 父评论ID
    private Long userId;
    private Long targetUserId; // 目标用户ID
    private List<CommentVO> children = new ArrayList<>();// 子评论(树形结构)
    private String userName; // 发表评论的用户名
    private String userAvatar; // 发表评论用户头像
    private String targetUserName; // 被回复的用户名
    private LocalDateTime createTime;

}
