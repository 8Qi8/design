package com.yyq.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVO {
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
    private List<Long> labelIds = new ArrayList<>();
}
