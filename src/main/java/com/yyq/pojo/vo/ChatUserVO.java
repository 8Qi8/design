package com.yyq.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatUserVO {
    private Long userId;           // 对方用户 ID
    private String username;       // 用户名
    private String avatar;         // 头像
    private String lastContent;    // 最新一条私信内容
    private LocalDateTime lastTime; // 最新私信时间
}
