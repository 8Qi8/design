package com.yyq.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowNotificationVO {
    private Long senderId;
    private String senderName;
    private String senderAvatar;
    private LocalDateTime createTime;
}
