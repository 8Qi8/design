package com.yyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.ArticleLabel;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleLabelMapper extends BaseMapper<ArticleLabel> {
    int insertBatch(@Param("list") List<ArticleLabel> list);

    int deleteBatch(List<Long> ids);

    @Select("SELECT label_id FROM article_label WHERE article_id = #{articleId}")
    List<Long> selectLabelIdsByArticleId(@Param("articleId") Long articleId);

}
