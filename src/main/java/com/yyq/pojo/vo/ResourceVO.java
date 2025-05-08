package com.yyq.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVO {
    private Integer id;

    private String title;

    private String description;

    private String fileUrl;

    private LocalDateTime uploadTime;

    private Integer downloadCount;

    private Integer uploaderId;
    private String userName;
    private String userAvatar;

}
