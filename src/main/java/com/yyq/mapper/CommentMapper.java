package com.yyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
