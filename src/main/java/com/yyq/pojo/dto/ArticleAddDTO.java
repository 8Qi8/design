package com.yyq.pojo.dto;

import com.yyq.pojo.entity.ArticleLabel;
import lombok.Data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleAddDTO{
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String summary;
    private String coverImage;
    private String status;
    private List<Long> labelIds = new ArrayList<>(); // 新增标签ID列表
}
