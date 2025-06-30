package com.yyq.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupVO {
    private Long id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signupTime;
    private LocalDateTime finishTime;
    private String note;

    private Long activityId;
    private String activityTitle;

    private Long userId;
    private String userName;
    private String name;
    private String phone;
    private String major;
}
