package com.yyq.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatVO {
    private Integer fanCount;
    private Integer articleCount;
    private Integer articleReadCount;
    private Integer articleCommentCount;
    private Integer articleLikeCount;
}
