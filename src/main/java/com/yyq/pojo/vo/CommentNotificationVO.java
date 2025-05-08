package com.yyq.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentNotificationVO {
    private Long senderId;
    private String senderName;
    private String senderAvatar;

    private String commentContent;

    private Long articleId;
    private String articleTitle;

    private LocalDateTime createTime;
}