package com.yyq.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private String type; // PRIVATE, COMMENT, LIKE, FOLLOW, SYSTEM
    private Integer isRead; //0 未读，1 已读
    private LocalDateTime createTime;
}