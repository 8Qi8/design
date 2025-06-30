package com.yyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.Article;
import com.yyq.pojo.entity.ArticleLike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {


}
