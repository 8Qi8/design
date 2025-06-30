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
@TableName("activity_signup")
public class ActivitySignup {
    private Long id;
    private Long userId;
    private Long activityId;
    private String name;
    private String phone;
    private String major;
    private String status;
    private LocalDateTime signupTime;
    private LocalDateTime finishTime;
    private String note;
}
